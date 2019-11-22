package com.ojas.rpo.security.dao.datareports;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Value;

import com.ojas.rpo.security.transfer.ReportResultsTransfer;
import com.ojas.rpo.security.transfer.ReportsListTransfer;



public class ReportsBarChartTemplates {

	@Value("${fileUploadPath}")
	private String documentsfolder;

	public String getSubmittedAndRejected(Integer totalSubmissions, Integer totalRejections) {
		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		try {

			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(totalSubmissions, "Submissions", "Submissions");
			my_bar_chart_dataset.addValue(totalRejections, "Rejections", "Rejections");

			/* Step -2:Define the JFreeChart object to create bar chart */
			JFreeChart BarChartObject = ChartFactory.createBarChart("Submissions Vs Rejections", "", "Count",
					my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);

			/* Get instance of CategoryPlot */
			CategoryPlot plot = BarChartObject.getCategoryPlot();
			//plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totalSubmissions > totalRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totalSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totalRejections/10)));
			}
			

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.red);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			String fileName = "submissionVsRejection" + new Date().hashCode();
			fileName = md5Java(fileName);
			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getClosuresAndJoining(Integer totalSubmissions, Integer totalRejections) {
		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		try {

			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(totalRejections, "Closures", "Closures");
			my_bar_chart_dataset.addValue(totalSubmissions, "Joinings", "Joinings");

			/* Step -2:Define the JFreeChart object to create bar chart */
			JFreeChart BarChartObject = ChartFactory.createBarChart("Closures Vs Joinings", "", "Count",
					my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);

			/* Get instance of CategoryPlot */
			CategoryPlot plot = BarChartObject.getCategoryPlot();
			//plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totalSubmissions > totalRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totalSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totalRejections/10)));
			}

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.orange);
			renderer.setSeriesPaint(1, Color.green);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			String fileName = "ClosuresVsJoinings" + new Date().hashCode() + "";
			fileName = md5Java(fileName);

			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			// converting byte array to Hexadecimal
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {

		} catch (NoSuchAlgorithmException ex) {

		}
		return digest;

	}

	public String getSubmittedAndClosures(Integer totalSubmissions, Integer totalRejections) {
		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		try {

			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(totalSubmissions, "Submissions", "Submissions");
			my_bar_chart_dataset.addValue(totalRejections, "Closures", "Closures");

			/* Step -2:Define the JFreeChart object to create bar chart */
			JFreeChart BarChartObject = ChartFactory.createBarChart("Submissions Vs Closures", "", "Count",
					my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);

			/* Get instance of CategoryPlot */
			CategoryPlot plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totalSubmissions > totalRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totalSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totalRejections/10)));
			}

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.ORANGE);
			renderer.setSeriesPaint(1, Color.GREEN);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			String fileName = "SubmissionVsClosures" + new Date().hashCode();
			fileName = md5Java(fileName);

			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {

		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getSubmissionsVsClosures() {

		return null;
	}

	public String getSubmissionsVsRejectionsByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(result.getTotalSubmissions(), "Submissions", "Submissions");
			my_bar_chart_dataset.addValue(result.getTotalRejections(), "Rejections", "Rejections");

			/* Step -2:Define the JFreeChart object to create bar chart */
			String username = null;
			/*
			 * if (null != result.getUserId()) { User user =
			 * userDao.find(result.getUserId()); username = user.getFirstName()
			 * + " " + user.getLastName(); BarChartObject =
			 * ChartFactory.createBarChart( " Submissions Vs  Rejections - " +
			 * result.getReportType() + "(" + result.getReportTypeValue() + ")",
			 * username, "Count", my_bar_chart_dataset,
			 * PlotOrientation.VERTICAL, true, true, false); plot =
			 * BarChartObject.getCategoryPlot();
			 * 
			 * } else {
			 */
			BarChartObject = ChartFactory.createBarChart(
					" Day wise Submissions Vs  Rejections - " + result.getReportType() + "("
							+ result.getReportTypeValue() + ")",
					" ", "Count", my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalSubmissions()/10)));
			if(result.getTotalSubmissions() > result.getTotalRejections()){
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalSubmissions()/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalRejections()/10)));
			}
			// plot.getDomainAxis()

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.red);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */ /*
														 * Height of the image
														 */
			String fileName = "SubmissionsVsRejections" + new Date().hashCode() + "";
			fileName = md5Java(fileName);
			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getSubmissionsVsClosuresByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(result.getTotalSubmissions(), "Submissions", "Submissions");
			my_bar_chart_dataset.addValue(result.getTotalRejections(), "Closures", "Closures");

			/* Step -2:Define the JFreeChart object to create bar chart */
			String username = null;
			/*
			 * if (null != result.getUserId()) { User user =
			 * userDao.find(result.getUserId()); username = user.getFirstName()
			 * + " " + user.getLastName(); BarChartObject =
			 * ChartFactory.createBarChart( " Submissions Vs  Rejections - " +
			 * result.getReportType() + "(" + result.getReportTypeValue() + ")",
			 * username, "Count", my_bar_chart_dataset,
			 * PlotOrientation.VERTICAL, true, true, false); plot =
			 * BarChartObject.getCategoryPlot();
			 * 
			 * } else {
			 */
			BarChartObject = ChartFactory.createBarChart(
					" Submissions Vs  Closures - " + result.getReportType() + "(" + result.getReportTypeValue() + ")",
					" ", "Count", my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(result.getTotalSubmissions() > result.getTotalRejections()){
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalSubmissions()/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalRejections()/10)));
			}
			// plot.getDomainAxis()

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.ORANGE);
			renderer.setSeriesPaint(1, Color.GREEN);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */ /*
														 * Height of the image
														 */
			String fileName = "SubmissionsVsClosures" + new Date().hashCode() + "";
			fileName = md5Java(fileName);
			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getClosersVsJoiningByReportType(ReportResultsTransfer result) {
		File BarChart = null;
		String base64Stream = null;
		ByteArrayOutputStream bos = null;		
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			my_bar_chart_dataset.addValue(result.getTotalSubmissions(), "Closures", "Closures");
			my_bar_chart_dataset.addValue(result.getTotalRejections(), "Joinings", "Joinings");

			/* Step -2:Define the JFreeChart object to create bar chart */
			String username = null;
			/*
			 * if (null != result.getUserId()) { User user =
			 * userDao.find(result.getUserId()); username = user.getFirstName()
			 * + " " + user.getLastName(); BarChartObject =
			 * ChartFactory.createBarChart( " Submissions Vs  Rejections - " +
			 * result.getReportType() + "(" + result.getReportTypeValue() + ")",
			 * username, "Count", my_bar_chart_dataset,
			 * PlotOrientation.VERTICAL, true, true, false); plot =
			 * BarChartObject.getCategoryPlot();
			 * 
			 * } else {
			 */
			BarChartObject = ChartFactory.createBarChart(
					" Closures Vs  Joinings - " + result.getReportType() + "(" + result.getReportTypeValue() + ")", " ",
					"Count", my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(result.getTotalSubmissions() > result.getTotalRejections()){
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalSubmissions()/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(result.getTotalRejections()/10)));
			}
			// plot.getDomainAxis()

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.orange);
			renderer.setSeriesPaint(1, Color.GREEN);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */ /*
														 * Height of the image
														 */
			String fileName = "ClosersVsJoinings" + new Date().hashCode() + "";
			fileName = md5Java(fileName);
			BarChart = new File(documentsfolder + "/" + fileName + ".png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);

		} catch (Exception i) {

		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getRecruiterWiseSubmissionsVsRejectionsByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		int totlaSubmissions =0;
		int totlaRejections =0;
		int count = 0;
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			Collection<ReportsListTransfer> list = result.getResultsList();
			if (list != null && list.isEmpty()) {
				count = list.size();
			}
			for (ReportsListTransfer data : list) {
				totlaSubmissions += data.getSubmissions();
				totlaRejections += data.getRejections();
				my_bar_chart_dataset.addValue(data.getSubmissions(), "Submissions", data.getNameOfUser());
				my_bar_chart_dataset.addValue(data.getRejections(), "Rejections", data.getNameOfUser());
			}

			/* Step -2:Define the JFreeChart object to create bar chart */

			BarChartObject = ChartFactory.createBarChart(
					"Recruiter wise Submissions Vs Rejections - " + result.getReportType() + "("
							+ result.getReportTypeValue() + ")",
					" ", "Count", my_bar_chart_dataset, PlotOrientation.HORIZONTAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totlaSubmissions > totlaRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totlaSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totlaRejections/10)));
			}
			

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.red);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 0;
			int height = 0;
			if (count > 4) {
				width = 1200; /* Width of the image */
			} else {
				width = 900;
			}
			if (count > 4) {
				height = 1000;
			} else {
				height = 1000;
			}

			/* Height of the image */
			BarChart = new File(documentsfolder + "/submissionVsRejection.png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);
			bos.close();
		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;

	}

	public String getRecruiterWiseClosuerVsJoinereByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		int count = 0;
		int totlaSubmissions = 0;
		int totlaRejections = 0;
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			Collection<ReportsListTransfer> list = result.getResultsList();
			if (list != null && list.size() > 0) {
				count = list.size();
			}
			for (ReportsListTransfer data : list) {
				totlaSubmissions += data.getSubmissions();
				totlaRejections += data.getRejections();
				my_bar_chart_dataset.addValue(data.getSubmissions(), "Clousers", data.getNameOfUser());
				my_bar_chart_dataset.addValue(data.getRejections(), "Joining", data.getNameOfUser());
			}

			/* Step -2:Define the JFreeChart object to create bar chart */

			BarChartObject = ChartFactory
					.createBarChart(
							"Recruiter wise Clousers Vs Joiners - " + result.getReportType() + "("
									+ result.getReportTypeValue() + ")",
							" ", "Count", my_bar_chart_dataset, PlotOrientation.HORIZONTAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
			//plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totlaSubmissions > totlaRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totlaSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totlaRejections/10)));
			}

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.green);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 0;
			if (count > 4) {
				width = 1200;
			} else {
				width = 1000;
			}
			int height = 1000; /* Height of the image */
			BarChart = new File(documentsfolder + "/submissionVsRejection.png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);
			bos.close();
		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;

	}

	public String getRecruiterWiseSubmissionsVsClousersByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		int totlaSubmissions = 0;
		int totlaRejections = 0;
		int count = 0;
		try {

			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			Collection<ReportsListTransfer> list = result.getResultsList();
			if (list != null && !list.isEmpty()) {
				count = list.size();
			}
			for (ReportsListTransfer data : list) {
				totlaSubmissions += data.getSubmissions();
				totlaRejections += data.getRejections();
				my_bar_chart_dataset.addValue(data.getSubmissions(), "Submissions", data.getNameOfUser());
				my_bar_chart_dataset.addValue(data.getRejections(), "Clousers", data.getNameOfUser());
			}

			/* Step -2:Define the JFreeChart object to create bar chart */

			BarChartObject = ChartFactory.createBarChart(
					"Recruiter wise Submissions Vs Clousers - " + result.getReportType() + "("
							+ result.getReportTypeValue() + ")",
					" ", "Count", my_bar_chart_dataset, PlotOrientation.HORIZONTAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
			//plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totlaSubmissions > totlaRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totlaSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totlaRejections/10)));
			}

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.MAGENTA);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 0;
			if (count > 4) {
				width = 1200;
			} else {
				width = 500;
			}
			/* Width of the image */
			int height = 1000; /* Height of the image */
			BarChart = new File(documentsfolder + "/submissionVsRejection.png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);
			bos.close();
		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;

	}

	public String getLeadOrAMWiseSubmissionsVsRejectionsByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		int totlaSubmissions = 0;
		int totlaRejections = 0;
		try {
			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			Collection<ReportsListTransfer> resultsList = result.getResultsList();
			for (ReportsListTransfer data : resultsList) {
				totlaSubmissions +=data.getSubmissions();
				totlaRejections += data.getRejections();
				my_bar_chart_dataset.addValue(data.getSubmissions(), "Submissions", data.getNameOfUser());
				my_bar_chart_dataset.addValue(data.getRejections(), "Rejections", data.getNameOfUser());
			}

			/* Step -2:Define the JFreeChart object to create bar chart */

			BarChartObject = ChartFactory.createBarChart(
					result.getUserRole() + " wise Submissions Vs Rejections - " + result.getReportType() + "("
							+ result.getReportTypeValue() + ")",
					" ", "Count", my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
			//plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totlaSubmissions > totlaRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totlaSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totlaRejections/10)));
			}

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.red);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setMaximumBarWidth(0.05);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			BarChart = new File(documentsfolder + "/submissionVsRejection.png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);
			bos.close();
		} catch (Exception i) {
			System.out.println(i);
		} finally {

			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}

	public String getRequirementVsSubmissionsByReportType(ReportResultsTransfer result) {

		String base64Stream = null;
		File BarChart = null;
		ByteArrayOutputStream bos = null;
		int totlaSubmissions = 0;
		int totlaRejections = 0;
		try {
			CategoryPlot plot = null;
			JFreeChart BarChartObject = null;
			/* Step - 1: Define the data for the bar chart */
			DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
			Collection<ReportsListTransfer> resultsList = result.getResultsList();
			for (ReportsListTransfer data : resultsList) {
				totlaSubmissions += data.getSubmissions();
				totlaRejections += data.getRejections();
				my_bar_chart_dataset.addValue(data.getSubmissions(), "Submissions", data.getRequirementId());
				my_bar_chart_dataset.addValue(data.getRejections(), "Rejections", data.getRequirementId());
			}

			/* Step -2:Define the JFreeChart object to create bar chart */

			BarChartObject = ChartFactory.createBarChart(
					result.getUserRole() + " wise Requirements Vs Submissions - " + result.getReportType() + "("
							+ result.getReportTypeValue() + ")",
					"Requirement ID", "Count", my_bar_chart_dataset, PlotOrientation.VERTICAL, true, true, false);
			plot = BarChartObject.getCategoryPlot();
		//	plot.getRangeAxis().setAutoRangeMinimumSize(10);
			NumberAxis range =(NumberAxis)plot.getRangeAxis();
			if(totlaSubmissions > totlaRejections){
				range.setTickUnit(new NumberTickUnit(Math.round(totlaSubmissions/10)));
			}else{
				range.setTickUnit(new NumberTickUnit(Math.round(totlaRejections/10)));
			}

			/* Get instance of CategoryPlot */

			/* Change Bar colors */
			BarRenderer renderer = (BarRenderer) plot.getRenderer();

			// renderer.setSeriesPaint(0, Color.red);
			renderer.setSeriesPaint(0, Color.blue);
			renderer.setSeriesPaint(1, Color.red);

			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);

			/*
			 * Step -3: Write the output as PNG file with bar chart information
			 */
			int width = 500; /* Width of the image */
			int height = 500; /* Height of the image */
			BarChart = new File(documentsfolder + "/reqVSsubmission.png");
			ChartUtilities.saveChartAsPNG(BarChart, BarChartObject, width, height);
			BufferedImage image = ImageIO.read(BarChart);

			bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			byte[] chatStream = bos.toByteArray();
			base64Stream = Base64.getEncoder().encodeToString(chatStream);
			bos.close();
		} catch (Exception i) {
			System.out.println(i);
		} finally {
			BarChart.delete();
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}
		return base64Stream;
	}
}
