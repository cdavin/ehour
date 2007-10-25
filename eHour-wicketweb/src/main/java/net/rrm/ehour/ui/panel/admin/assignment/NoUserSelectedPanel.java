/**
 * Created on Oct 23, 2007
 * Created by Thies Edeling
 * Created by Thies Edeling
 * Copyright (C) 2007 TE-CON, All Rights Reserved.
 *
 * This Software is copyright TE-CON 2007. This Software is not open source by definition. The source of the Software is available for educational purposes.
 * TE-CON holds all the ownership rights on the Software.
 * TE-CON freely grants the right to use the Software. Any reproduction or modification of this Software, whether for commercial use or open source,
 * is subject to obtaining the prior express authorization of TE-CON.
 * 
 * thies@te-con.nl
 * TE-CON
 * Legmeerstraat 4-2h, 1058ND, AMSTERDAM, The Netherlands
 *
 */

package net.rrm.ehour.ui.panel.admin.assignment;

import net.rrm.ehour.ui.border.GreyRoundedBorder;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.dojo.markup.html.form.DojoDatePicker;

/**
 * Same as NoEntrySelected just with a hidden dojo date picker. Bug in DOjo prevents
 * to load the dojo js through ajax, needs to be there on initial load
 **/

public class NoUserSelectedPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoUserSelectedPanel(String id)
	{
		super(id);
		
		this.setOutputMarkupId(true);
		Border greyBorder = new GreyRoundedBorder("border", 500);
		add(greyBorder);

		greyBorder.add(new Label("noEntry", new ResourceModel("admin.assignment.noUserSelected")));

		// this is what we call a hack sir
		final DojoDatePicker dateStart = new DojoDatePicker("dummyDate", new Model(), "dd/MM/yyyy");
		greyBorder.add(dateStart);
		
	}
}