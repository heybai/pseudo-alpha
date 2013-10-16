package com.heybai.pseudoalpha.ws;

import java.io.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

/**
 * @author Ivan Grishchenko (heybai@)
 * @since 15.10.13
 */
@Path("/file")
public class UploadFileService {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String uploaded = getStringFromInputStream(uploadedInputStream);
        String result = solve(uploaded);
        String output = makePrettyOutput(result);

        return Response.status(200).entity(output).build();

    }

    private String solve(String mathScript) {
        String jLinkDir = "c:\\Program Files\\Wolfram Research\\Mathematica\\9.0\\SystemFiles\\Links\\JLink\\";
        System.setProperty("com.wolfram.jlink.libdir", jLinkDir);

        KernelLink kernel;
        String result = "";
        try {
            kernel = MathLinkFactory.createKernelLink("-linkmode launch -linkname 'c:\\Program Files\\Wolfram Research\\Mathematica\\9.0\\MathKernel.exe'");
            kernel.discardAnswer();
            result = kernel.evaluateToInputForm(mathScript, 0);
            kernel.terminateKernel();
        } catch (MathLinkException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            // DO NOTHING
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // DO NOTHING
                }
            }
        }

        return sb.toString();
    }

    private String makePrettyOutput(String mathOutput) {
        mathOutput = mathOutput.replaceAll("\\{\"" , "\"");
        mathOutput = mathOutput.replaceAll("\"\\|", "<br/>|");
        mathOutput = mathOutput.replaceAll("\\|b", "&#948;");
        mathOutput = mathOutput.replaceAll("--(\\d,\\d)", "<sub>$1</sub>");
        mathOutput = mathOutput.replaceAll("\"," , " = ");
        mathOutput = mathOutput.replaceAll("Subscript\\[(.*?),\\s*(.*?)\\]" , "$1<sub>$2</sub>");

        return mathOutput;
    }

}
