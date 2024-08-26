package com.cafe.util.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.cafe.util.NegocioException;

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
    	File file = new File(SEPARATOR + "home" + SEPARATOR + "uploads");
    	
    	if (!file.exists())
			file.mkdirs();

		log.info(file.getAbsolutePath());
        return file;
    }
    
    /*
     * Alternativa para dounload no IfSuldeMinas 
     */
    public static void downloadStream(String url) throws Exception {

		String arquivoPath = url;

		log.info(arquivoPath);

		try {			
			
			File file = new File(arquivoPath);
			
			log.info(file.getName());
			
			InputStream fis = new FileInputStream(file);
			
			byte[] buf = new byte[1000000];
			int offset = 0;
			int numRead = 0;
			while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
				offset += numRead;
			}
			
			fis.close();
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
			response.getOutputStream().write(buf);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		}
		catch(Exception e) {
			throw new NegocioException("Houve um problema na recuperação do Documento!");
		}		
	}
    
    public static void downloadDesktop(String url) throws Exception {

		String arquivoPath = url;

		log.info(arquivoPath);

		File arquivoPDF = new File(arquivoPath);

		if (arquivoPDF.exists()) {

			try {
			// Abra o arquivo PDF com o aplicativo padrão associado
			Desktop.getDesktop().open(arquivoPDF);
			}
			catch(Exception e){
				throw new NegocioException("Houve um problema para baixar o documento.");
			}
		}
	}
}
