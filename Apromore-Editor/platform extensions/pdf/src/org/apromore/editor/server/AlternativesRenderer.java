package org.apromore.editor.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

public class AlternativesRenderer extends HttpServlet {

    private static final long serialVersionUID = 8526319871562210085L;

    private String inFile;
    private String outFile;

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        String data = req.getParameter("data");

        try {
            data = new String(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String tmpPath = this.getServletContext().getRealPath("/") + File.separator + "tmp" + File.separator;

        // create tmp folder
        File tmpFolder = new File(tmpPath);
        if (!tmpFolder.exists()) {
            tmpFolder.mkdirs();
        }

        String baseFilename = String.valueOf(System.currentTimeMillis());
        this.inFile = tmpPath + baseFilename + ".svg";
        this.outFile = tmpPath + baseFilename + ".pdf";

        try {
            String contextPath = req.getContextPath();
            BufferedWriter out = new BufferedWriter(new FileWriter(inFile));
            out.write(data);
            out.close();
            makePDF();
            res.getOutputStream().print(contextPath + "/tmp/" + baseFilename + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void makePDF() throws TranscoderException, IOException {
        PDFTranscoder transcoder = new PDFTranscoder();
        InputStream in = new java.io.FileInputStream(inFile);

        try {
            TranscoderInput input = new TranscoderInput(in);

            // Setup output
            OutputStream out = new java.io.FileOutputStream(outFile);
            out = new java.io.BufferedOutputStream(out);
            try {
                TranscoderOutput output = new TranscoderOutput(out);

                // Do the transformation
                transcoder.transcode(input, output);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

}
