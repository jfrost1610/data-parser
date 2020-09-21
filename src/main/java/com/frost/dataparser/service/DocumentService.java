package com.frost.dataparser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frost.dataparser.model.DocumentDetails;
import com.frost.dataparser.writer.DataWriterFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentService {

	@Autowired
	private DataWriterFactory writerFactory;

	public void addDataToDocument(DocumentDetails documentDetails) {
		log.info("Getting Writer!");
		boolean status = writerFactory.getWriter(documentDetails.getType()).write(documentDetails);
		log.info("Add Data to Document status : {}", status);
	}

	public void updateDataOnDocument(DocumentDetails documentDetails) {
		log.info("Getting Writer!");
		boolean status = writerFactory.getWriter(documentDetails.getType()).update(documentDetails);
		log.info("Update Data to Document status : {}", status);
	}

}
