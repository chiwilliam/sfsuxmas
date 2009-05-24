package com.sfsu.xmas.kegg;

import com.sfsu.xmas.data_structures.Probes;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;
import keggapi.*;

public class PathwayAccessor {

    public static void main(String[] args) throws Exception {
        KEGGLocator locator = new KEGGLocator();
        KEGGPortType serv = locator.getKEGGPort();

//        String query = "path:eco00020"; //args[0];
//        String[] results = serv.get_genes_by_pathway(query);
//
//        for (int i = 0; i < results.length; i++) {
//            System.out.println(results[i]);
//        }

// Returns the URL of the generated image for the given map 'path:eco00260'
// with objects corresponding to 'eco:b0002' and 'cpd:C00263' colored in red.
//        String[] obj_list = new String[]{
//            "eco:b0002",
//            "cpd:C00263"
//        };


// Returns the URL for the given pathway 'path:eco00260' with genes
// 'eco:b0514' colored in red with yellow background and
// 'eco:b2913' colored in green with yellow background.
        String[] obj_list = new String[]{"eco:b0514", "eco:b2913"};
        String[] fg_list = new String[]{"green", "red"};
        String[] bg_list = new String[]{"purple", "yellow"};
        String url = serv.get_html_of_colored_pathway_by_objects("path:eco00260", obj_list, fg_list, bg_list);
//String url = serv.color_pathway_by_objects("path:eco00260", obj_list, fg_list, bg_list);

//        String url = serv.mark_pathway_by_objects("path:eco00260", obj_list);
//
        System.out.println("URL:");
        System.out.println(url);

    // Convert NCBI GI and NCBI GeneID to KEGG genes_id
//        serv.bconv("ncbi-gi:10047086 ncbi-gi:10047090 ncbi-geneid:14751");
    }

    public static String getPathwayURLForProbes(Probes probes) {
        String url = "";
        try {
            KEGGLocator locator = new KEGGLocator();
            KEGGPortType serv = locator.getKEGGPort();
            String[] obj_list = new String[]{"eco:b0514", "eco:b2913"};
            String[] fg_list = new String[]{"green", "red"};
            String[] bg_list = new String[]{"purple", "yellow"};
            url = serv.get_html_of_colored_pathway_by_objects("path:eco00260", obj_list, fg_list, bg_list);
        } catch (RemoteException ex) {
            Logger.getLogger(PathwayAccessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(PathwayAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
}