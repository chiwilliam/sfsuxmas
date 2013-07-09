package com.sfsu.xmas.rss;

import com.sfsu.xmas.servlet.MsgTemplates;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SGetLatestUpdates extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

//            URL feedUrl = new URL("http://xmasupdates.blogspot.com/feeds/posts/default");
//
//            SyndFeedInput input = new SyndFeedInput();
//            SyndFeed feed = input.build(new XmlReader(feedUrl));
//            
//            List<SyndEntryImpl> entries = feed.getEntries();
//
//            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
//            df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
//
//            Iterator<SyndEntryImpl> it = entries.iterator();
//            
//            int entryCount = 0;
//            
//            while (it.hasNext() && entryCount < 3) {
//                SyndEntryImpl entry = it.next();
//                out.write("<p>");
//                out.write("<b>");
//                out.write("<a href=\"" + entry.getLink() + "\" target=\"_blank\">");
//                out.write(entry.getTitle());
//                out.write("</a>");
//                out.write("</b><small><i> by " + entry.getAuthor() + "</i> on " + df.format(entry.getPublishedDate()) + "</small>");
//                out.write("<br />");
//                out.write("<div style=\"margin-left: 15px;\">");
//
//                List<SyndContentImpl> contents = entry.getContents();
//
//                Iterator<SyndContentImpl> contIt = contents.iterator();
//                while (contIt.hasNext()) {
//                    SyndContentImpl sci = contIt.next();
//                    String value = sci.getValue();
//                    if (value.length() > 300) {
//                        value = value.substring(0, Math.min(300, value.length())) + "<a href=\"" + entry.getLink() + "\" target=\"_blank\">...</a>";
//                    }
//                    out.write(value);
//                }
//                out.write("</div>");
//                out.write("</p>");
//                entryCount++;
//            }
//            
//            if (it.hasNext()) {
//                out.write("<p style=\"text-align: right;\">");
//                out.write("<b>");
//                
//                out.write("<a href=\"" + feed.getLink() + "\" target=\"_blank\" alt=\"" + feed.getTitle() + "\">More...</a>");
//                out.write("</b>");
//                out.write("</p>");
//            }
//
//        } catch (UnknownHostException ex) {
//            out.write(MsgTemplates.getError("Unable to retrieve updates"));
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(SGetLatestUpdates.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FeedException ex) {
//            Logger.getLogger(SGetLatestUpdates.class.getName()).log(Level.SEVERE, null, ex);
            out.write("See <a href=\"http://xmasupdates.blogspot.com\" target=\"_blank\">http://xmasupdates.blogspot.com</a>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
