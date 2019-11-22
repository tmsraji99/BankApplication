
package com.ojas.rpo.security.rest.resources;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Clob;
import java.util.Iterator;

import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.resourceEntity.ResourceEntityDao;
import com.ojas.rpo.security.entity.ResourceEntity;
import com.ojas.rpo.security.entity.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/addResource")
public class ResourceEntityResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	

	@POST
	@Path("/uploadResource")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadResource(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails) {

		try {

			byte[] bytes = IOUtils.toByteArray(fileInputString);

			Workbook workbook = new XSSFWorkbook(fileInputString);

			ResourceEntity resourceEntity = new ResourceEntity();
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					String requirementName = nextCell.getStringCellValue();
					// resourceEntity.setRequirementName(requirementName);
					String datedateofapplication = nextCell.getStringCellValue();
					String sourceOfApplication = nextCell.getStringCellValue();
					String firstName = nextCell.getStringCellValue();
					String emailID = nextCell.getStringCellValue();
					Long phoneNumber = (long) nextCell.getNumericCellValue();
					String currentLocation = nextCell.getStringCellValue();
					String preferredLocations = nextCell.getStringCellValue();
					String totalExperience = nextCell.getStringCellValue();
					String currCompanyname = nextCell.getStringCellValue();
					String currCompanyDesignation = nextCell.getStringCellValue();
					String functionalArea = nextCell.getStringCellValue();
					String role = nextCell.getStringCellValue();
					String industry = nextCell.getStringCellValue();
				//	Clob keySkills=nextCell.getStringCellValue();

					Double annualSalary = nextCell.getNumericCellValue();
					Long noticePeriod = (long) nextCell.getNumericCellValue();
					String resumeHeadline = nextCell.getStringCellValue();
					String Summary = nextCell.getStringCellValue();
					String underGraduationdegree = nextCell.getStringCellValue();
					String ugSpecialization = nextCell.getStringCellValue();
					String ugUniversity = nextCell.getStringCellValue();
					String instituteName = nextCell.getStringCellValue();
					String ugGraduation = nextCell.getStringCellValue();
					String Postgraduation = nextCell.getStringCellValue();
					String degree = nextCell.getStringCellValue();
					
				

				}

			}

			System.out.println(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
