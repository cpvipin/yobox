package com.org.yobox.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.org.yobox.common.AppConstants;
import com.org.yobox.common.Validation;
import com.org.yobox.context.SubscriptionStatus;
import com.org.yobox.controller.BaseController;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.notify.MailSender;
import com.org.yobox.util.AppUtils;
import com.org.yobox.util.CommonUtils;

public class EmailNotifier implements AfterReturningAdvice, AppConstants {

	private Collection pointCuts;

	private static Logger logger = (Logger) Logger
			.getInstance(EmailNotifier.class);

	/**
	 * This constructor is invoked when the singleton for this class is created.
	 * advices.xml *
	 * 
	 * @param methodNames
	 */
	public EmailNotifier(List pointCuts) {
		this.pointCuts = pointCuts;
	}

	public EmailNotifier() {

	}

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if (this.pointCuts.contains(method.getName())) {
			if (CommonUtils.isNormalizeStringEquals(method.getName(),
					"updateCustomerSubscription")) {
				CustomerSubscription customerSubs = (CustomerSubscription) args[0];
				if (!CommonUtils.isEmpty(customerSubs.getCustomer().getEmail())) {
					sendOrderConfirmEmail(customerSubs);

				}
			}

			else if (CommonUtils.isNormalizeStringEquals(method.getName(),
					"addPasswordResetToken")) {
				PasswordRequestHistory passHis = (PasswordRequestHistory) args[0];
				if (Validation.isValidEmail(passHis.getRequestedUserId()))
					sendPasswordResetLink(passHis);
			}
		}

	}

	public void sendOrderConfirmEmail(CustomerSubscription customerSubs) {

		StringBuffer msgBody = new StringBuffer();
		MailSender mailSender = MailSender.getSoleInstance();
		String mailSubject = "Order confirmed";
		String customerName=!CommonUtils.isEmpty(customerSubs.getCustomer().getFullName())?customerSubs.getCustomer().getFullName():"";

		msgBody.append(getHeader());

		msgBody.append("<table class='body-wrap'><tr><td></td><td class='container' bgcolor='#FFFFFF'><div class='content'><table><tr><td><br/>");
		msgBody.append("<h3>Thanks " + customerName
				+ "</h3>");
		msgBody.append("<p><img src='http://localhost:8080/yobox/img/home_bg.jpg' alt='' /></p><br/>");
		msgBody.append("<h4>Your Order <small>(#" + customerSubs.getId() + ") "
				+ customerSubs.getStartDate() + "</small></h4>");
		msgBody.append("<p>Thank you for placing your order with Yobox. This email is to confirm your order has been placed successfully, and will be processed & shipped to you soon.</p>");
		msgBody.append("<br/><div class='products'><img src='https://beta.fashtag.in/img/logo.png' />");
		msgBody.append("<span>Yobox for" + AppUtils.getSubscriptionName()
				+ " / " + customerSubs.getSubscriptionCycle().getPrice()
				+ "</span>");
		msgBody.append("</div><hr><div style='clear:both;'></div><br/><br/><h5><b>Subtotal: Rs.</b>"
				+ customerSubs.getSubscriptionCycle().getPrice()
				+ "</h5><br/><br/>");
		msgBody.append("<h4><b> Total :"
				+ customerSubs.getSubscriptionCycle().getPrice() + "</b></h4>");
		msgBody.append("<br/><table class='columns' width='100%'><tr><td><table align='left' class='column'><tr><td><h5 class=''>Shipping address</h5><p class=''>");
		msgBody.append(customerSubs.getCustomerAddress().getFullName()
				+ "<br/>" + customerSubs.getCustomerAddress().getAddress()
				+ "<br/>" + customerSubs.getCustomerAddress().getLocality()
				+ "<br/>"
				+ customerSubs.getCustomerAddress().getState().getName()
				+ "<br/>" + customerSubs.getCustomerAddress().getPinCode()
				+ "<p/>");

		msgBody.append("</td></tr></table><table align='left' class='column'><tr><td><h5 class=''>Shipping address</h5><p class=''>");
		msgBody.append(customerSubs.getCustomerAddress().getFullName()
				+ "<br/>" + customerSubs.getCustomerAddress().getAddress()
				+ "<br/>" + customerSubs.getCustomerAddress().getLocality()
				+ "<br/>"
				+ customerSubs.getCustomerAddress().getState().getName()
				+ "<br/>" + customerSubs.getCustomerAddress().getPinCode()
				+ "<p/>");
		msgBody.append("</p></td></tr></table><span class='clear'></span></td></tr></table><br/><p style='text-align:center;'><a class='btn' href='https://beta.fashtag.in/'>Log in to view your order </a></p>");
		msgBody.append("</td></tr></table></div></td><td></td></tr></table>");
		msgBody.append(getFooter());

		mailSender.sendFormattedMessage(BaseController
				.getAppSystemProperties(AppConstants.SUPPORT_EMAIL),
				customerSubs.getCustomer().getEmail(), "", "", mailSubject,
				msgBody.toString(), "");

	}

	public void sendPasswordResetLink(PasswordRequestHistory passHist) {
		StringBuffer msgBody = new StringBuffer();
		MailSender mailSender = MailSender.getSoleInstance();
		String mailSubject = "Password reset link";
		msgBody.append(getHeader());

		msgBody.append("<table width='100%' style='background-color: white; vertical-align: top; margin-bottom: 10px;' cellpadding='15' cellspacing='0'><tr><td><div><p>");
		msgBody.append("<strong>Dear User,</strong></p><p><strong></strong><br>Please click below link for resetting your password.</p>");
		msgBody.append("<p><a href='"
				+ BaseController
						.getAppSystemProperties(AppConstants.APP_BASE_URL)
				+ "/resetPassword.htm?token="
				+ passHist.getToken()
				+ "&requestedUserId="
				+ passHist.getRequestedUserId()
				+ "'>Password Reset Link Click Here. <br/> If there any difficulties in contact us at </a>"
				+ BaseController
						.getAppSystemProperties(AppConstants.SUPPORT_EMAIL)
				+ "  </p><p>Regards,<br>YOBOX team - Making people beautiful!</p><p><br><br></p></div>");
		msgBody.append("</td></tr></table>");

		msgBody.append(getFooter());
		mailSender.sendFormattedMessage(BaseController
				.getAppSystemProperties(AppConstants.SUPPORT_EMAIL), passHist
				.getCustomer().getEmail(), "", "", mailSubject, msgBody
				.toString(), "");

	}

	private String getHeader() {
		StringBuffer msgHeader = new StringBuffer();
		msgHeader
				.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		msgHeader.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		msgHeader.append("<head>");
		msgHeader
				.append("<meta name='viewport' content='width=device-width' />");
		msgHeader
				.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
		msgHeader.append("<title>Order Confirmed</title>");
		msgHeader.append("<style>");
		msgHeader.append("	* { ");
		msgHeader.append("		margin:0;");
		msgHeader.append("		padding:0;");
		msgHeader.append("	}");
		msgHeader
				.append("	* { font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; }");
		msgHeader.append(" img { ");
		msgHeader.append("max-width: 100%; ");
		msgHeader.append("}");
		msgHeader.append(".collapse {");
		msgHeader.append("margin:0;");
		msgHeader.append("padding:0;");
		msgHeader.append("}");
		msgHeader.append("body {");
		msgHeader.append("-webkit-font-smoothing:antialiased; ");
		msgHeader.append("-webkit-text-size-adjust:none; ");
		msgHeader.append("width: 100%!important; ");
		msgHeader.append("height: 100%;");
		msgHeader.append("}");
		msgHeader.append("a { color: #2BA6CB;}");

		msgHeader.append("	.btn {");
		msgHeader.append("	text-decoration:none;");
		msgHeader.append("	color:#FFF;");
		msgHeader.append("	background-color:#071d49;");
		msgHeader.append("	width:80%;");
		msgHeader.append("	padding:15px 10%;");
		msgHeader.append("	font-weight:bold;");
		msgHeader.append("	text-align:center;");
		msgHeader.append("	cursor:pointer;");
		msgHeader.append("	display:inline-block;");
		msgHeader.append(" }");

		msgHeader.append(" p.callout {");
		msgHeader.append("	padding:15px;");
		msgHeader.append("	text-align:center;");
		msgHeader.append("	background-color:#ECF8FF;");
		msgHeader.append("	margin-bottom: 15px;");
		msgHeader.append("}");
		msgHeader.append(" .callout a {");
		msgHeader.append("	font-weight:bold;");
		msgHeader.append("	color: #2BA6CB;");
		msgHeader.append(" }");

		msgHeader.append(" .column table { width:100%;}");
		msgHeader.append(" .column {");
		msgHeader.append("	width: 300px;");
		msgHeader.append("	float:left;");
		msgHeader.append(" }");
		msgHeader.append(" .column tr td { padding: 15px; }");
		msgHeader.append("	.column-wrap { ");
		msgHeader.append("		padding:0!important; ");
		msgHeader.append("		margin:0 auto; ");
		msgHeader.append("		max-width:600px!important;");
		msgHeader.append("	}");
		msgHeader.append("	.columns .column {");
		msgHeader.append("		width: 280px;");
		msgHeader.append("		min-width: 279px;");
		msgHeader.append("		float:left;");
		msgHeader.append("	}");
		msgHeader
				.append("	table.columns, table.column, .columns .column tr, .columns .column td {");
		msgHeader.append("		padding:0;");
		msgHeader.append("		margin:0;");
		msgHeader.append("		border:0;");
		msgHeader.append("		border-collapse:collapse;");
		msgHeader.append("	}");
		msgHeader.append("	table.head-wrap { width: 100%;}");

		msgHeader.append("	.header.container table td.logo { padding: 15px; }");
		msgHeader
				.append("	.header.container table td.label { padding: 15px; padding-left:0px;}");
		msgHeader.append("	table.body-wrap { width: 100%;}");
		msgHeader
				.append("	table.footer-wrap { width: 100%;	clear:both!important;");
		msgHeader.append("	}");
		msgHeader
				.append("	.footer-wrap .container td.content  p { border-top: 1px solid rgb(215,215,215); padding-top:15px;}");
		msgHeader.append("	.footer-wrap .container td.content p {");
		msgHeader.append("		font-size:10px;");
		msgHeader.append("		font-weight: bold;");

		msgHeader.append("	}");
		msgHeader.append("	h1,h2,h3,h4,h5,h6 {");
		msgHeader
				.append("	font-family: 'HelveticaNeue-Light', 'Helvetica Neue Light', 'Helvetica Neue', Helvetica, Arial, 'Lucida Grande', sans-serif; line-height: 1.1; margin-bottom:15px; color:#000;");
		msgHeader.append("	}");
		msgHeader
				.append("	h1 small, h2 small, h3 small, h4 small, h5 small, h6 small { font-size: 60%; color: #6f6f6f; line-height: 0; text-transform: none; }");

		msgHeader.append("	h1 { font-weight:200; font-size: 44px;}");
		msgHeader.append("	h2 { font-weight:200; font-size: 37px;}");
		msgHeader.append("	h3 { font-weight:500; font-size: 27px;}");
		msgHeader.append("	h4 { font-weight:500; font-size: 23px;}");
		msgHeader.append("	h5 { font-weight:900; font-size: 17px;}");
		msgHeader
				.append("	h6 { font-weight:900; font-size: 14px; text-transform: uppercase; color:#444;}");

		msgHeader.append("	.collapse { margin:0!important;}");

		msgHeader.append("	p, ul { ");
		msgHeader.append("		margin-bottom: 10px; ");
		msgHeader.append("		font-weight: normal; ");
		msgHeader.append("		font-size:14px; ");
		msgHeader.append("		line-height:1.6;");
		msgHeader.append("	}");
		msgHeader.append("	p.lead { font-size:17px; }");
		msgHeader.append("	p.last { margin-bottom:0px;}");

		msgHeader.append("	ul li {");
		msgHeader.append("		margin-left:5px;");
		msgHeader.append("		list-style-position: inside;");
		msgHeader.append("	}");

		msgHeader.append("	hr {");
		msgHeader.append("	    border: 0;");
		msgHeader.append("	    height: 0;");
		msgHeader.append("	    border-top: 1px dotted rgba(0, 0, 0, 0.1);");
		msgHeader
				.append("	    border-bottom: 1px dotted rgba(255, 255, 255, 0.3);");
		msgHeader.append("	}");
		msgHeader.append("	.products {");
		msgHeader.append("		width:100%;");
		msgHeader.append("		height:40px;");
		msgHeader.append("		margin:10px 0 10px 0;");
		msgHeader.append("	}");
		msgHeader.append("	.products img {");
		msgHeader.append("		float:left;");
		msgHeader.append("		height:40px;");
		msgHeader.append("		width:auto;");
		msgHeader.append("		margin-right:20px;");
		msgHeader.append("	}");
		msgHeader.append("	.products span {");
		msgHeader.append("		font-size:17px;");
		msgHeader.append("	}");
		msgHeader.append("	.container {");
		msgHeader.append("		display:block!important;");
		msgHeader.append("		max-width:600px!important;");
		msgHeader.append("		margin:0 auto!important; ");
		msgHeader.append("		clear:both!important;");
		msgHeader.append("	}");
		msgHeader.append("	.content {");
		msgHeader.append("		padding:15px;");
		msgHeader.append("		max-width:600px;");
		msgHeader.append("		margin:0 auto;");
		msgHeader.append("		display:block; ");
		msgHeader.append("	}");
		msgHeader.append("	.content table { width: 100%; }");

		msgHeader.append("	.clear { display: block; clear: both; }");
		msgHeader.append("	@media only screen and (max-width: 600px) {");

		msgHeader
				.append("		a[class='btn'] { display:block!important; margin-bottom:10px!important; background-image:none!important; margin-right:0!important;}");

		msgHeader
				.append("		div[class='column'] { width: auto!important; float:none!important;}");

		msgHeader.append("		table.social div[class='column'] {");
		msgHeader.append("			width:auto!important;");
		msgHeader.append("		}");

		msgHeader.append("	}");
		msgHeader.append(" </style>");

		msgHeader.append(" </head>");

		msgHeader.append(" <body bgcolor='#FFFFFF'>");

		msgHeader.append("<table class='head-wrap' bgcolor='#ff3366'>");
		msgHeader.append("	<tr>");
		msgHeader.append("		<td></td>");
		msgHeader.append("		<td class='header container'>");

		msgHeader.append("				<div class='content'>");
		msgHeader.append("					<table bgcolor='#ff3366'>");
		msgHeader.append("						<tr>");
		msgHeader.append("							<td>");
		msgHeader
				.append("								<a href='#' title='Yobox' alt='Yobox' /><img src='https://beta.fashtag.in/img/logo.png' style='width:70px;height:auto;' /></a>");
		msgHeader.append("							</td>");
		msgHeader.append("							<td align='right'>");
		msgHeader.append("								<h6 class='collapse'>Order Confirmed</h6>");
		msgHeader.append("							</td>");
		msgHeader.append("						</tr>");
		msgHeader.append("				</table>");
		msgHeader.append("				</div>");

		msgHeader.append("		</td>");
		msgHeader.append("		<td></td>");
		msgHeader.append("	</tr>");
		msgHeader.append("</table>");

		return msgHeader.toString();

	}

	private String getFooter() {
		StringBuffer msgFooter = new StringBuffer();
		msgFooter.append("<table class='footer-wrap' bgcolor='#ff3366'>");
		msgFooter.append("	<tr>");
		msgFooter.append("		<td></td>");
		msgFooter.append("		<td class='container'>");

		msgFooter.append("				<div class='content'>");
		msgFooter.append("				<table>");
		msgFooter.append("				<tr>");
		msgFooter.append("					<td align='center'>");
		msgFooter
				.append("						<p>Thank you for shopping at <a href='' style='color:#111111;'>Yobox</a>!</p>");
		msgFooter
				.append("						<a href='' title='{{ shop_name }}'><img src='https://beta.fashtag.in/img/logo.png' style='width:40px;height:auto;' alt='{{ shop_name }}' /></a>");
		msgFooter.append("						<br/><br/>");
		msgFooter
				.append("						<p><strong><a href='{{ shop.email }}' style='color:#111111;'>support@getYobox.com</a></strong></p>");
		msgFooter.append("						<p><small>© YoBox</small></p>");
		msgFooter.append("					</td>");
		msgFooter.append("				</tr>");
		msgFooter.append("			</table>");
		msgFooter.append("				</div>");

		msgFooter.append("		</td>");
		msgFooter.append("		<td></td>");
		msgFooter.append("	</tr>");
		msgFooter.append("</table>");

		msgFooter.append("</body>");
		msgFooter.append("</html>");

		return msgFooter.toString();
	}

}
