/**
 * Created on Jul 13, 2007
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

package net.rrm.ehour.ui.report.reports.aggregate;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.rrm.ehour.report.reports.ProjectAssignmentAggregate;
import net.rrm.ehour.report.reports.ReportDataAggregate;
import net.rrm.ehour.ui.common.DummyDataGenerator;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO 
 **/

public class CustomerReportTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testInitializeReportDataAggregate()
	{
		ProjectAssignmentAggregate pag1 = DummyDataGenerator.getProjectAssignmentAggregate(1, 1, 1);
		ProjectAssignmentAggregate pag2 = DummyDataGenerator.getProjectAssignmentAggregate(2, 2, 1);
		
		List<ProjectAssignmentAggregate> pags = new ArrayList<ProjectAssignmentAggregate>();
		
		pags.add(pag1);
		pags.add(pag2);
		
		ReportDataAggregate reportDataAggregate = new ReportDataAggregate(pags, null, null);
		
		CustomerReport report = new CustomerReport();
		report.initialize(reportDataAggregate);
		
		assertEquals(2, report.getReportNodes().size());	
	}
}