package com.ojas.rpo.security.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class WordToPdf {

	public static void convertWordtoPdf(String srcfileName, String targetFilePath, String regenerationFilePath)
			throws IOException {

		/*
		 * try { InputStream is = new FileInputStream(new File(srcfileName));
		 * XWPFDocument document = new XWPFDocument(is); OutputStream out = new
		 * FileOutputStream(targetFilePath); PdfOptions options = null;
		 * PdfConverter.getInstance().convert(document, out, options); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */

		try {
			if (srcfileName.endsWith(".doc")) {
				convertDocToPDF(srcfileName, targetFilePath, regenerationFilePath);
			}
			if (srcfileName.endsWith(".docx")) {
				convertDocxToPDF(srcfileName, targetFilePath, regenerationFilePath);
			}
			if (srcfileName.endsWith(".pdf")) {
				regenerateFile(srcfileName, regenerationFilePath);
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void convertDocxToPDF(String srcfileName, String targetFilePath, String regenerationFilePath)
			throws Exception {

		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(srcfileName));
			document.createNumbering();
			PdfOptions options = PdfOptions.create();
			OutputStream out = new FileOutputStream(targetFilePath);
			System.out.println("PDF Converter Object : " + PdfConverter.getInstance());
			PdfConverter.getInstance().convert(document, out, options);

			PdfReader reader = new PdfReader(targetFilePath);
			int n = reader.getNumberOfPages();
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(regenerationFilePath));
			Resource resource = new ClassPathResource("images/ojas-logo.png");
			InputStream input = resource.getInputStream();
			Image img = Image.getInstance(StreamUtils.copyToByteArray(input));
			img.setAbsolutePosition(250, 720);

			PdfImage stream = new PdfImage(img, "", null);
			stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
			PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
			img.setDirectReference(ref.getIndirectReference());
			PdfContentByte over = stamper.getOverContent(1);
			over.addImage(img);

			Resource resource2 = new ClassPathResource("images/ojas-logo.png");
			InputStream input2 = resource2.getInputStream();
			Image imgMark = Image.getInstance(StreamUtils.copyToByteArray(input2));
			imgMark.setAbsolutePosition(200, 400);
			float w = imgMark.getScaledWidth();
			float h = imgMark.getScaledHeight();

			PdfContentByte under;
			Rectangle pagesize;
			float x, y;

			// loop over every page
			int np = reader.getNumberOfPages();
			for (int i = 1; i <= np; i++) {

				// get page size and position
				pagesize = reader.getPageSizeWithRotation(i);
				x = (pagesize.getLeft() + pagesize.getRight()) / 2;
				y = (pagesize.getTop() + pagesize.getBottom()) / 2;
				under = stamper.getOverContent(i);
				under.saveState();

				// set transparency
				PdfGState state = new PdfGState();
				state.setFillOpacity(0.2f);
				under.setGState(state);

				// add watermark text and image
				under.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
				under.restoreState();
			}
			stamper.close();
			reader.close();

			File existingFile = new File(targetFilePath);
			existingFile.delete();

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void convertDocToPDF(String srcfileName, String targetFilePath, String regenerationFilePath)
			throws Exception {

		try {
			WordprocessingMLPackage wordMLPackage = getMLPackage(new FileInputStream(srcfileName));
			Docx4J.toPDF(wordMLPackage, new FileOutputStream(targetFilePath));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	protected static WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception {
		WordprocessingMLPackage mlPackage = null;
		try {
			PrintStream originalStdout = System.out;

			// Disable stdout temporarily as Doc convert produces alot of output
			System.setOut(new PrintStream(new OutputStream() {
				public void write(int b) {
					// DO NOTHING
				}
			}));

			mlPackage = Doc.convert(iStream);

			System.setOut(originalStdout);

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return mlPackage;
	}

	static void regenerateFile(String srcfileName, String regenerationFilePath) {
		try {
			PdfReader reader = new PdfReader(srcfileName);
			int n = reader.getNumberOfPages();
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(regenerationFilePath));
			Resource resource = new ClassPathResource("images/ojas-logo.png");
			InputStream input = resource.getInputStream();
			Image img = Image.getInstance(StreamUtils.copyToByteArray(input));
			img.setAbsolutePosition(250, 720);

			PdfImage stream = new PdfImage(img, "", null);
			stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
			PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
			img.setDirectReference(ref.getIndirectReference());
			PdfContentByte over = stamper.getOverContent(1);
			over.addImage(img);

			Resource resource2 = new ClassPathResource("images/ojas-logo.png");
			InputStream input2 = resource2.getInputStream();
			Image imgMark = Image.getInstance(StreamUtils.copyToByteArray(input2));
			imgMark.setAbsolutePosition(200, 400);
			float w = imgMark.getScaledWidth();
			float h = imgMark.getScaledHeight();

			PdfContentByte under;
			Rectangle pagesize;
			float x, y;

			// loop over every page
			int np = reader.getNumberOfPages();
			for (int i = 1; i <= np; i++) {

				// get page size and position
				pagesize = reader.getPageSizeWithRotation(i);
				x = (pagesize.getLeft() + pagesize.getRight()) / 2;
				y = (pagesize.getTop() + pagesize.getBottom()) / 2;
				under = stamper.getOverContent(i);
				under.saveState();

				// set transparency
				PdfGState state = new PdfGState();
				state.setFillOpacity(0.2f);
				under.setGState(state);

				// add watermark text and image
				under.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
				under.restoreState();
			}
			stamper.close();
			reader.close();

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
