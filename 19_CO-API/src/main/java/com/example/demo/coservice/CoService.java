package com.example.demo.coservice;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.corepository.Arrepository;
import com.example.demo.corepository.CoTriggerRepository;
import com.example.demo.corepository.EdEligRepository;
import com.example.demo.corepository.caseRepo;
import com.example.demo.entity.CitizenAppEntity;
import com.example.demo.entity.CoResponse;
import com.example.demo.entity.CoTrigEntity;
import com.example.demo.entity.DcCaseEntity;
import com.example.demo.entity.EDEligDtlsEntity;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CoService {
	
	private CoTriggerRepository coTrgRepo;
	private EdEligRepository eligRepo;
	private caseRepo dcCaseRepo;
	private Arrepository appRepo;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;


	 Long failed = 0l;
	 Long success =0l;

	public CoService(CoTriggerRepository coTrgRepo, EdEligRepository eligRepo, caseRepo dcCaseRepo,
			Arrepository appRepo) {
		super();
		this.coTrgRepo = coTrgRepo;
		this.eligRepo = eligRepo;
		this.dcCaseRepo = dcCaseRepo;
		this.appRepo = appRepo;
	}

	public CoResponse processPendingTriggers() throws Exception {

		HashOperations<String,Object,Object> hashops=redisTemplate.opsForHash();
		String addr=(String)hashops.get("DHS","DHS_OFC_ADDRESS");
//		System.err.println(addr+"bbb");
		CoResponse response = new CoResponse();
		List<CoTrigEntity> pendingTrgs = coTrgRepo.findByTrgStatus("pending");
		ExecutorService exservice = Executors.newFixedThreadPool(10);
		System.err.println(pendingTrgs);
		for (CoTrigEntity trigger : pendingTrgs) {
//			processTrigger(response, trigger,addr);
			System.err.println(trigger);
			exservice.submit(new Callable<Object>() {
				public Object call()throws Exception{
					processTrigger(response, trigger,addr);
					success++;
					return null;
				}
			});
		}

		response.setTotalTriggers(Long.valueOf(pendingTrgs.size()));
		response.setSuccTriggers(success);
		response.setFailedTrigger(failed);
		return response;

	}

	private CitizenAppEntity processTrigger(CoResponse response, CoTrigEntity entity,String footer) throws Exception {
		CitizenAppEntity appEntity = null;
		// get eligibility data based on casenum
		EDEligDtlsEntity elig = eligRepo.findByCasenumber(entity.getCaseNumber());
		// get citizen data based on case num

		Optional<DcCaseEntity> findById = dcCaseRepo.findById(entity.getCaseNumber());
		if (findById.isPresent()) {
			Integer appid = findById.get().getAppid();
			Optional<CitizenAppEntity> appEntityOptional = appRepo.findById(appid);
			if (appEntityOptional.isPresent()) {
				appEntity = appEntityOptional.get();

			}
		}
		generateAndSendPdf(elig, appEntity,footer);
		return appEntity;
	}

	private void generateAndSendPdf (EDEligDtlsEntity eligData, CitizenAppEntity appEntity,String footer) throws Exception{
		Document document = new Document(PageSize.A4);
	File file = new File(eligData.getCasenumber() + ".pdf"); 
	FileOutputStream fos = null;
	try {
	fos = new FileOutputStream(file); 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	PdfWriter.getInstance(document, fos);
	document.open();
	Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	font.setColor(Color.BLUE);
	font.setSize(18);
	String[] addrTokens=footer.split("#");
	String hno=addrTokens[0];
	String street=addrTokens[1];
	String city=addrTokens[2];
	String phno=addrTokens[3];
	String email=addrTokens[4];
	String website=addrTokens[5];
	
	String footerstr="H.NO: "+hno+" Street: "+street+" City: "+city+" phno: "+phno+" Email: "+email+" website: "+website;
	Paragraph fooParagraph=new Paragraph(footerstr,font);
	
	
	Paragraph p = new Paragraph ("Eligibility Report", font);
	p.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(p);
	PdfPTable table = new PdfPTable(6); 
	table.setWidthPercentage (100f); 
	table.setWidths (new float[] { 2.5f, 3.5f, 3.0f, 2.5f, 3.0f, 3.5f});
	table.setSpacingBefore(15);
	
	PdfPCell cell = new PdfPCell();
	cell.setBackgroundColor(Color.BLUE);
	cell.setPadding(6);
	font = FontFactory.getFont(FontFactory.HELVETICA); 
	font.setColor(Color.WHITE);
//	cell.setPhrase (new Phrase("Citizen Name", font));
//	table.addCell(cell);
	cell.setPhrase (new Phrase("Plan Name", font));
	table.addCell(cell); 
	cell.setPhrase(new Phrase("Plan Status", font));
	table.addCell(cell);
	cell.setPhrase (new Phrase("Plan Start Date", font)); 
	table.addCell(cell);
	cell.setPhrase (new Phrase("Plan End Date",font));
	table.addCell(cell);
	cell.setPhrase (new Phrase("Benefit Amount", font));
	table.addCell(cell);
	cell.setPhrase (new Phrase("Denial Reason", font)); 
	table.addCell(cell);
//	table.addCell(appEntity.getFname());
	System.err.println(eligData);
	table.addCell(eligData.getPlanName());
	table.addCell(eligData.getPlanStatus());
	table.addCell(eligData.getPlanStartDate() + "");
	table.addCell(eligData.getPlanEndDate() + ""); 
	table.addCell(eligData.getBenifitAmount() + "");
	table.addCell(eligData.getDenialReason() + "");
	
	
	//get footer data and write to pdf;
	

	document.add(table);
	document.add(fooParagraph);
	document.close();
	
	String subject = "HIS Eligiblity Info"; 
	String body ="HIS Eligiblity Info"; 
	
	// emailUtils.sendEmail(appEntity.getEmail(), subject, body, file); 
	updateTrigger(eligData.getCasenumber(),file);
//	file.delete();
	
}

	private void updateTrigger(Long caseNum, File file) throws Exception {
		CoTrigEntity coEntity = coTrgRepo.findByCaseNumber(caseNum);
		byte[] arr = new byte[(int)file.length()];
		System.err.println(file.length());
		FileInputStream fis = new FileInputStream(file); 
		fis.read(arr);
		coEntity.setPdf(arr);
		coEntity.setTrgStatus("Completed");
		coTrgRepo.save(coEntity);
		fis.close();
	}
	
}
