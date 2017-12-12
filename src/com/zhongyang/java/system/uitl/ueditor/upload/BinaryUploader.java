package com.zhongyang.java.system.uitl.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.zhongyang.java.system.uitl.ueditor.PathFormat;
import com.zhongyang.java.system.uitl.ueditor.define.AppInfo;
import com.zhongyang.java.system.uitl.ueditor.define.BaseState;
import com.zhongyang.java.system.uitl.ueditor.define.FileType;
import com.zhongyang.java.system.uitl.ueditor.define.State;

public class BinaryUploader {
	
	private static Logger logger=Logger.getLogger(BinaryUploader.class);

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		logger.info("===================="+request.getSession().getId());
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;
		logger.info("===================="+isAjaxUpload);
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);
			logger.info("=================="+iterator);
			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);
			originFileName=System.currentTimeMillis()+suffix;
//			originFileName = originFileName.substring(0,
//					originFileName.length() - suffix.length());
			savePath = savePath + originFileName;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = savePath;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				//storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("url", originFileName);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
