/**
 * 
 */
package com.frost.dataparser.writer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.frost.dataparser.model.DataModel;
import com.frost.dataparser.model.DocumentDetails;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Service("CSV")
public class CSVDataWriter implements DataWriter {

	@Value("${storage.path}")
	private String storagePath;

	@PostConstruct
	@Override
	public void initialize() throws IOException {

		log.info("Initializing CSVDataWriter");

		File file = new File(getFilePath());
		if (!file.isFile()) {
			log.info("Creating CSVDataFile at {}", getFilePath());
			try (FileWriter outputfile = new FileWriter(file); CSVWriter writer = new CSVWriter(outputfile);) {
				String[] metaData = { "0" };
				writer.writeNext(metaData);
				String[] header = { "Id", "Name", "DOB", "Salary" };
				writer.writeNext(header);
			}
		}

		log.info("CSVDataWriter Ready");

	}

	@Override
	public boolean write(DocumentDetails document) {

		try {

			initialize();
			appendData(document);

			return true;
		} catch (Exception e) {
			log.error("Failed to add Data to CSV due to an exception!", e);
			return false;
		}
	}

	@Override
	public boolean update(DocumentDetails document) {

		try {
			updateData(document);
			return true;
		} catch (Exception e) {
			log.error("Failed to update Data to CSV due to an exception!", e);
			return false;
		}
	}

	@Override
	public DocumentDetails readAll() throws IOException {

		File dataFile = new File(getFilePath());
		DocumentDetails documents = new DocumentDetails();
		documents.setType("CSV");

		List<DataModel> dataModels = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(dataFile))) {

			String[] metaData = reader.readNext();
			documents.setSize(Integer.parseInt(metaData[0]));

			String[] headers = reader.readNext();
			documents.setHeaders(Arrays.asList(headers));

			String[] nextRecord;

			while ((nextRecord = reader.readNext()) != null) {
				DataModel dataModel = new DataModel(nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3]);
				dataModels.add(dataModel);
			}

			documents.setDatas(dataModels);

		}

		return documents;

	}

	private void appendData(DocumentDetails document) throws IOException {

		int size = 0;
		List<String[]> csvBody;

		File dataFile = new File(getFilePath());

		try (CSVReader reader = new CSVReader(new FileReader(dataFile))) {
			String[] metaData = reader.readNext();
			size = Integer.parseInt(metaData[0]);
		}

		try (FileWriter outputfile = new FileWriter(dataFile, true); CSVWriter writer = new CSVWriter(outputfile);) {

			for (DataModel data : document.getDatas()) {
				size++;
				String[] dataArray = { String.valueOf(size), data.getName(), data.getDob(), data.getSalary() };
				writer.writeNext(dataArray);

			}

		}

		try (CSVReader reader = new CSVReader(new FileReader(dataFile))) {
			csvBody = reader.readAll();
			csvBody.get(0)[0] = String.valueOf(size);
		}

		try (CSVWriter writer = new CSVWriter(new FileWriter(dataFile))) {
			writer.writeAll(csvBody);
			writer.flush();
		}

	}

	private void updateData(DocumentDetails document) throws IOException {

		List<String[]> csvBody;
		File dataFile = new File(getFilePath());

		try (CSVReader reader = new CSVReader(new FileReader(dataFile))) {
			csvBody = reader.readAll();

			for (DataModel data : document.getDatas()) {

				int matchingRowNbr = -1;

				for (String[] csvLine : csvBody) {
					matchingRowNbr++;
					if (csvLine[0].equals(data.getId())) {
						csvBody.get(matchingRowNbr)[1] = data.getName();
						csvBody.get(matchingRowNbr)[2] = data.getDob();
						csvBody.get(matchingRowNbr)[3] = data.getSalary();
						break;
					}
				}

			}

		}

		try (CSVWriter writer = new CSVWriter(new FileWriter(dataFile))) {
			writer.writeAll(csvBody);
			writer.flush();
		}

	}

	@Override
	public String getFilePath() {
		return storagePath + "/datafile.csv";
	}

}
