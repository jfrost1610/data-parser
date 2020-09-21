/**
 * 
 */
package com.frost.dataparser.model;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class Documents {

	private String userId;
	private DocumentDetails csvDocument;
	private DocumentDetails xmlDocument;

}
