/**
 * Created on May 29, 2007
 * Created by Thies Edeling
 * Copyright (C) 2005, 2006 te-con, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * thies@te-con.nl
 * TE-CON
 * Legmeerstraat 4-2h, 1058ND, AMSTERDAM, The Netherlands
 *
 */

package net.rrm.ehour.ui.page.login;

import java.io.Serializable;

import net.rrm.ehour.ui.page.admin.assignment.AssignmentPage;
import net.rrm.ehour.ui.page.admin.mainconfig.MainConfig;
import net.rrm.ehour.ui.session.EhourWebSession;
import net.rrm.ehour.ui.util.CommonStaticData;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Login page 
 **/

public class Login extends WebPage
{
	private static final long serialVersionUID = -134022212692477120L;
	private	static Logger logger = Logger.getLogger(Login.class);

	/**
	 * 
	 */
	public Login()
	{
		this(null);
	}

	/**
	 * Check if the session exists, kill the session and redirect to the homepage.
	 * This will trigger the authentication but at least the redirect is properly setup
	 * @param parameters page parameters (ignored)
	 */
	public Login(final PageParameters parameters)
	{
		EhourWebSession session = (EhourWebSession)getSession();
		
		if (session.isSignedIn())
		{
			if (logger.isInfoEnabled())
			{
				logger.info("User already signed in, logging out");
			}
			
			session.signOut();
			setResponsePage(getApplication().getHomePage());
		}
		else
		{
			setupForm();
		}
	}
	
	private void setupForm()
	{
		//add(new StyleSheetReference("loginStyle", new CompressedResourceReference(LoginPage.class, "style/ehourLogin.css")));

		add(new Label("pageTitle", new ResourceModel("login.login.header")));
		add(new SignInForm("loginform", new SimpleUser()));

	}

	/**
	 * The class <code>SignInForm</code> is a subclass of the Wicket
	 * {@link Form} class that attempts to authenticate the login request using
	 * Wicket auth (which again delegates to Acegi Security).
	 */
	public final class SignInForm extends Form
	{
		private static final long serialVersionUID = -4355842488508724254L;

		/**
		 * 
		 * @param id
		 * @param model
		 */
		public SignInForm(String id, SimpleUser model)
		{
			super(id, new CompoundPropertyModel(model));

			FeedbackPanel	feedback = new LoginFeedbackPanel("feedback");
			feedback.setMaxMessages(1);
			
			add(feedback);
			add(new TextField("username").setRequired(true));
			add(new PasswordTextField("password").setResetPassword(true));
			add(new Button("signin", new ResourceModel("login.login.submit")));
		}

		/**
		 * Called upon form submit. Attempts to authenticate the user.
		 */
		protected void onSubmit()
		{
			if (EhourWebSession.getSession().isSignedIn())
			{
				// now this really shouldn't happen as the session is killed in the constructor
				error("already logged in");

			} else
			{
				SimpleUser user = ((SimpleUser) getModel().getObject());
				String username = user.getUsername();
				String password = user.getPassword();

				// Attempt to authenticate.
				EhourWebSession session = (EhourWebSession) Session.get();
				
				// When authenticated decide the redirect
				if (session.signIn(username, password))
				{
					Class<? extends WebPage> homepage = getHomepageForRole(session.getRoles());
					
					if (logger.isDebugEnabled())
					{
						logger.debug("User '" + username + "' redirected to " + homepage.getName());
					}
					
					setResponsePage(homepage);
				}
				else
				{
					error(getLocalizer().getString("login.login.failed", this));
				}
			}

			// ALWAYS do a redirect, no matter where we are going to. The point is that the
			// submitting page should be gone from the browsers history.
			setRedirect(true);
		}
		
		/**
		 * TODO externalize?
		 * @param roles
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private Class<? extends WebPage> getHomepageForRole(Roles roles)
		{
			Class<? extends WebPage>	homepage;
			
			if (roles.contains(CommonStaticData.ROLE_ADMIN))
			{
				homepage = MainConfig.class;
			}
			else if (roles.contains(CommonStaticData.ROLE_REPORT))
			{
				// TODO replace with report
				homepage = AssignmentPage.class;
			}
			else
			{
				homepage = getApplication().getHomePage();
			}
		
			return homepage;
		}
	}
	
	public final class LoginFeedbackPanel extends FeedbackPanel
	{
		/**
		 * @see org.apache.wicket.Component#Component(String)
		 */
		public LoginFeedbackPanel(final String id)
		{
			super(id);
		}		
	}

	/**
	 * Simple bean that represents the properties for a login attempt (username
	 * and clear text password).
	 */
	public static class SimpleUser implements Serializable
	{
		private static final long serialVersionUID = -5617176504597041829L;

		private String username;
		private String password;

		public String getUsername()
		{
			return username;
		}

		public void setUsername(String username)
		{
			this.username = username;
		}

		public String getPassword()
		{
			return password;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}
	}
}