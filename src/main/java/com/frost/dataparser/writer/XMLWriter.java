/**
 * 
 */
package com.frost.dataparser.writer;

import org.springframework.stereotype.Service;

import com.frost.dataparser.model.DocumentDetails;

/**
 * @author jobin
 *
 */
@Service("XML")
public class XMLWriter implements DataWriter {

	@Override
	public boolean write(DocumentDetails document) {
		return false;
	}

	@Override
	public boolean update(DocumentDetails document) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

}
