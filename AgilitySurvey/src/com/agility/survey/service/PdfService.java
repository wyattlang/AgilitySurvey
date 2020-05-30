package com.agility.survey.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.agility.survey.pojo.Choice;
import com.agility.survey.pojo.Question;
import com.agility.survey.pojo.Score;
import com.agility.survey.pojo.Survey;
import com.agility.survey.pojo.User;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

	public void create(User user, Survey survey) {
		for(Question q : survey.getQuestions()) {
			for(Choice c : q.getChoices()) {
				System.out.println(c.isSelected());
			}
			System.out.println(q.getScore());
		}
		Document document = new Document();
		String fileName = String.format("%s.pdf", user.getDiscountCode());
		Path path = Paths.get("C:/Users/Wyatt/eclipse-ee-workspace/AgilitySurvey/pdf/" + fileName);
		try {
			Files.createFile(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			PdfWriter.getInstance(document, new FileOutputStream(path.toString()));
			document.open();
			Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
			Font boldFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);
			document.add(new Paragraph("Intro: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", font));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("What you're doing well:", boldFont));
			Question[] highQuestions = Arrays.stream(survey.getQuestions())
					                         .filter(x -> x.getScore() == Score.HIGH)
					                         .toArray(Question[]::new);
			List highList = new List();
			for(Question q : highQuestions) {
				highList.add(new ListItem(q.getSupportingSentenceByGrade(), font));
			}
			document.add(highList);
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Where you're on track:", boldFont));
			Question[] mediumQuestions = Arrays.stream(survey.getQuestions())
						                       .filter(x -> x.getScore() == Score.MEDIUM)
						                       .toArray(Question[]::new);
			List mediumList = new List();
			for(Question q : mediumQuestions) {
				mediumList.add(new ListItem(q.getSupportingSentenceByGrade(), font));
			}
			document.add(mediumList);
			document.add(new Paragraph(" "));
			document.add(new Paragraph("What needs improvement:", boldFont));
			Question[] lowQuestions = Arrays.stream(survey.getQuestions())
                    						.filter(x -> x.getScore() == Score.LOW)
                    						.toArray(Question[]::new);
			List lowList = new List();
			for(Question q : lowQuestions) {
				lowList.add(new ListItem(q.getSupportingSentenceByGrade(), font));
			}			
			document.add(lowList);
			document.add(new Paragraph(" "));
			Paragraph discountCodeParagraph = new Paragraph(new Phrase("Discount Code:", boldFont));
			discountCodeParagraph.add(new Phrase(user.getDiscountCode(), font));
			document.add(discountCodeParagraph);
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
}
