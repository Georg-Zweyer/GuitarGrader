/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.handlers;

import java.util.*;
import java.io.*;

import org.wahlzeit.model.*;
import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;
import org.wahlzeit.webparts.*;

import zweyer.georg.adap.wahlzeit.model.GPSLocation;
import zweyer.georg.adap.wahlzeit.model.Guitar;
import zweyer.georg.adap.wahlzeit.model.GuitarFactory;
import zweyer.georg.adap.wahlzeit.model.GuitarManager;
import zweyer.georg.adap.wahlzeit.model.GuitarManufacturer;
import zweyer.georg.adap.wahlzeit.model.GuitarPhoto;
import zweyer.georg.adap.wahlzeit.model.GuitarType;
import zweyer.georg.adap.wahlzeit.model.GuitarTypeManager;
import zweyer.georg.adap.wahlzeit.model.MapcodeLocation;

/**
 * 
 * @author dirkriehle
 *
 */
public class UploadPhotoFormHandler extends AbstractWebFormHandler {
	
	/**
	 *
	 */
	public UploadPhotoFormHandler() {
		initialize(PartUtil.UPLOAD_PHOTO_FORM_FILE, AccessRights.USER);
	}
	
	/**
	 * 
	 */
	protected void doMakeWebPart(UserSession us, WebPart part) {
		Map<String, Object> args = us.getSavedArgs();
		part.addStringFromArgs(args, UserSession.MESSAGE);

		part.maskAndAddStringFromArgs(args, Photo.TAGS);
		
		
		StringBuffer buffer = new StringBuffer();
		Collection<GuitarType> list = GuitarTypeManager.getInstance().loadGuitarTypes();
		for (GuitarType guitarType : list) {
			buffer.append("<option ");
		    buffer.append("value=\""+guitarType.getId()+"\"");
		    if(guitarType.getId().equals(-1)) {
		    	buffer.append(" selected");
		    }
		    buffer.append(">");
		    buffer.append(guitarType.getName());
		    buffer.append("</option>");
		}
		
		StringBuffer buffer2 = new StringBuffer();
		Collection<Guitar> list2 = GuitarManager.getInstance().loadGuitars();
		for (Guitar guitar : list2) {
			buffer2.append("<option ");
		    buffer2.append("value=\""+guitar.getId()+"\"");
		    if(guitar.getId().equals(-1)) {
		    	buffer2.append(" selected");
		    }
		    buffer2.append(">");
		    buffer2.append(guitar.getYearBuilt()+" "+guitar.getColor()+": "+guitar.getType().getName());
		    buffer2.append("</option>");
		}
		
		part.addString("guitar", buffer2.toString());
		part.addString("guitarType", buffer.toString());
	}
	
	/**
	 * 
	 */
	protected String doHandlePost(UserSession us, Map args) {
		String tags = us.getAndSaveAsString(args, Photo.TAGS);
		
		// get the POST variables for location data
		String latitude = us.getAndSaveAsString(args, "lat");
		String longitude = us.getAndSaveAsString(args, "long");
		String mapcode = us.getAndSaveAsString(args, "mapcode");
		
		// get the POST variables for domain data
		String newGuitar = us.getAndSaveAsString(args, "newGuitar");
		
		String guitarId = us.getAndSaveAsString(args, "guitarId");
		
		String guitarColor = us.getAndSaveAsString(args, "guitarColor");
		String guitarYearBuilt = us.getAndSaveAsString(args, "guitarYearBuilt");
		
		String newGuitarType = us.getAndSaveAsString(args, "newGuitarType");
		
		String guitarTypeId = us.getAndSaveAsString(args, "guitarTypeId");
		
		String guitarName = us.getAndSaveAsString(args, "guitarName");
		String guitarManufacturer = us.getAndSaveAsString(args, "guitarManufacturer");
		
		if (!StringUtil.isLegalTagsString(tags)) {
			us.setMessage(us.cfg().getInputIsInvalid());
			return PartUtil.UPLOAD_PHOTO_PAGE_NAME;
		}

		try {
			PhotoManager pm = PhotoManager.getInstance();
			String sourceFileName = us.getAsString(args, "fileName");
			File file = new File(sourceFileName);
			Photo photo = pm.createPhoto(file);

			String targetFileName = SysConfig.getBackupDir().asString() + photo.getId().asString();
			createBackup(sourceFileName, targetFileName);
		
			User user = (User) us.getClient();
			user.addPhoto(photo); 
			
			photo.setTags(new Tags(tags));
			
			// add location data to the photo if correct data is given. do nothing if invalid data is given.
			if (!latitude.isEmpty() && !longitude.isEmpty()) {
				try {
					photo.setLocation(new GPSLocation(latitude+","+longitude));
				} catch (AssertionError ae) {
					
				}
			} else if (!mapcode.isEmpty()) {
				try {
					photo.setLocation(new MapcodeLocation(mapcode));
				} catch (AssertionError ae) {
					
				}
			} 
			// add domain data to the photo if correct data is given and the Photo is a domain Photo. do nothing if invalid data is given.
			if(photo instanceof GuitarPhoto) {
				GuitarManager gm = GuitarManager.getInstance();
				GuitarTypeManager gtm = GuitarTypeManager.getInstance();
				if(newGuitar.equals("0")) {
					Guitar guitar = gm.getGuitarFromId(Integer.decode(guitarId));
					((GuitarPhoto) photo).setGuitar(guitar);
				} else if (newGuitar.equals("1")){
					Guitar guitar = gm.createGuitar();
					guitar.setColor(guitarColor);
					guitar.setYearBuilt(Integer.decode(guitarYearBuilt));
					if (newGuitarType.equals("0")){
						guitar.setType(gtm.getGuitarTypeFromId(Integer.decode(guitarTypeId)));
					} else if (newGuitarType.equals("1")) {
						GuitarType type = gtm.createGuitarType();
						type.setManufacturer(GuitarManufacturer.getInstance(guitarManufacturer));
						type.setName(guitarName);
						guitar.setType(type);
						gtm.saveGuitarType(type);//needed cause i don't know when it gets saved otherwise cause saveAll() is not called if the server is terminated.
					}
					((GuitarPhoto) photo).setGuitar(guitar);
					gm.saveGuitar(guitar);//needed cause i don't know when it gets saved otherwise cause saveAll() is not called if the server is terminated.				
					}
			}
			
			pm.savePhoto(photo);

			StringBuffer sb = UserLog.createActionEntry("UploadPhoto");
			UserLog.addCreatedObject(sb, "Photo", photo.getId().asString());
			UserLog.log(sb);
			
			us.setTwoLineMessage(us.cfg().getPhotoUploadSucceeded(), us.cfg().getKeepGoing());
		} catch (Exception ex) {
			SysLog.logThrowable(ex);
			us.setMessage(us.cfg().getPhotoUploadFailed());
		}
		
		return PartUtil.UPLOAD_PHOTO_PAGE_NAME;
	}
	
	/**
	 * 
	 */
	protected void createBackup(String sourceName, String targetName) {
		try {
			File sourceFile = new File(sourceName);
			InputStream inputStream = new FileInputStream(sourceFile);
			File targetFile = new File(targetName);
			OutputStream outputStream = new FileOutputStream(targetFile);
			// @FIXME IO.copy(inputStream, outputStream);
		} catch (Exception ex) {
			SysLog.logSysInfo("could not create backup file of photo");
			SysLog.logThrowable(ex);			
		}
	}
}
