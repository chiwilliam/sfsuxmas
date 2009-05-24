/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data_upload;

import com.sfsu.xmas.data_install.SUploadData;
import com.sfsu.xmas.globals.FileGlobals;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author bdalziel
 */
public class SUploadFile extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
//            factory.setRepository(new File("web/files"));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                // Parse the request
                List /* FileItem */ items = upload.parseRequest(request);

                // Process the uploaded items
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {
                    } else {
                        String fileName = item.getName();
                        ServletContext sc = getServletContext();
                        String path = sc.getRealPath(FileGlobals.getRoot());

                        File uploadedFile = new File(path, fileName);
                        try {
                            item.write(uploadedFile);

//                        InputStream uploadedStream = item.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
//
//                        new FileReader(databaseName, reader, tps);
//
//                        //                (uploadedStream);
//
//                        uploadedStream.close();
//                processUploadedFile(item);
                        } catch (Exception ex) {
                            Logger.getLogger(SUploadFile.class.getName()).log(Level.SEVERE, null, ex);
                        }



//                        InputStream uploadedStream = item.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedStream));
//
//                        new FileReader(databaseName, reader, tps);
//
//                        //                (uploadedStream);
//
//                        uploadedStream.close();
//                processUploadedFile(item);
                    }
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(SUploadData.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        String urlString = "data/your_data.jsp";
        response.sendRedirect(urlString);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
