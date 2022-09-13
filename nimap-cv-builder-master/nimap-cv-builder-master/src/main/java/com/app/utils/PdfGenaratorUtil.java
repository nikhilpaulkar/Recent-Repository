package com.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class PdfGenaratorUtil {

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${secure-file.cv-dir}")
	private URL templateLocation;

	public String createPdf(String templateName, Map map) throws Exception {
		System.out.println("map bb"+map);
		Assert.notNull(templateName, "The templateName can not be null");
		Context ctx = new Context();

		if (map != null) {

			Iterator itMap = map.entrySet().iterator();

			while (itMap.hasNext()) {

				Map.Entry pair = (Map.Entry) itMap.next();
				ctx.setVariable(pair.getKey().toString(), pair.getValue());

			}

		}

		String processedHtml = templateEngine.process(templateName, ctx);
		FileOutputStream os = null;
		String fileName = UUID.randomUUID().toString();

		try {

			File pathAsFile = new File(templateLocation.getPath());

			if (!Files.exists(Paths.get(templateLocation.getPath()))) {

				pathAsFile.mkdir();

			}

			File tempDirectory = new File(templateLocation.getPath());
			File outputFile = new File(tempDirectory, fileName + ".pdf");
			// final File outputFile = File.createTempFile(fileName, ".pdf");
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(processedHtml);
			renderer.layout();
			renderer.createPDF(os, false);
			renderer.finishPDF();
		
			return  outputFile.getPath();

		} finally {

			if (os != null) {

				try {

					os.close();

				} catch (IOException e) {

					/* ignore */ }

			}

		}

	}

}
