package com.cafe.util.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lombok.extern.log4j.Log4j;

@Log4j
public class PdfUtil {

    public static File escrever(String name, byte[] contents) throws IOException {
        File file = new File(diretorio(), name);

        OutputStream out = new FileOutputStream(file);
        out.write(contents);
        out.close();

        return file;    
    }
        
    public static File diretorio() throws IOException {

    	//separador de arquivos '\' ou '/' dependendo do Sistema Operacional
    	final String SEPARATOR = System.getProperty("file.separator");
    	File file = new File("c:" + SEPARATOR + "temp" + SEPARATOR + "pdfs");
    	
    	if (!file.exists())
			file.mkdirs();

		log.info(file.getAbsolutePath());
        return file;
    }
}
