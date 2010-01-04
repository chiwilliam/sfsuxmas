<% String _title = "Search & Filter Operations";%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        
        <title><%= _title %> :: ExperiGen &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../styles/modules.css" />
        
        <script type="text/javascript" src="../script/filter_page.js"></script>
        
    </head>
    
    <body onload="return window_onload()">
        <div class="ajax_table_container">
            
            <%

            String error = request.getParameter("error");
            if (error != null) {
                String errorString = "";
                int errorCode = Integer.parseInt(error);
                switch (errorCode) {
                    case 1:
                        errorString = "Filter not specified - please select a filter from section 1";
                        break;
                    case 2:
                        errorString = "Affy ID unknown";
                        break;
                    default:
                        errorString = "Error unknown";
                }
            %>
            <div class="error"><b>Error Code <%= errorCode %></b>: <%= errorString %></div>
            <%
            }
            %>
            
            <h2>1. Choose Filter</h2>
            
            <div id="gene_filter">
                <form action="../SFilterManipulator?filterType=4" method="POST" target="_parent">
                    <table>
                        <tr>
                            <th>Affymetrix Gene ID:</th>
                            <td><input TYPE="TEXT" NAME="affyID" style="width: 190px;"> <i><small>[e.g. 37145_at]</small></i></td>
                        </tr>
                        <tr>
                            <th>Internal Gene ID:</th>
                            <td><input TYPE="TEXT" NAME="geneID" style="width: 190px;"> <i><small>[e.g. 50]</small></i></td>
                        </tr>
                        <tr>
                            <th>Pathway:</th>
                            <td>
                                <select name="pathwayID" style="width: 200px;">
                                    <option value=""></option>
                                    <option value="29">1- and 2-Methylnaphthalene degradation</option>
                                    <option value="227">ACE-Inhibitor_pathway_PharmGKB</option>
                                    <option value="234">Acetylcholine_Synthesis</option>
                                    <option value="22">Acetylcholine_Synthesis</option>
                                    <option value="54">Adherens junction</option>
                                    <option value="136">Adipocytokine signaling pathway</option>
                                    <option value="143">Alanine and aspartate metabolism</option>
                                    <option value="60">Alanine and aspartate metabolism</option>
                                    <option value="75">Alkaloid biosynthesis I</option>
                                    <option value="65">Alkaloid biosynthesis II</option>
                                    <option value="176">Alzheimer's disease</option>
                                    <option value="171">Aminoacyl-tRNA synthetases</option>
                                    <option value="111">Aminophosphonate metabolism</option>
                                    <option value="177">Aminosugars metabolism</option>
                                    <option value="213">Amyotrophic lateral sclerosis (ALS)</option>
                                    <option value="238">Androgen and estrogen metabolism</option>
                                    <option value="14">Antigen processing and presentation</option>
                                    <option value="119">Apoptosis</option>
                                    <option value="187">Apoptosis</option>
                                    <option value="149">Apoptosis_GenMAPP</option>
                                    <option value="191">Apoptosis_KEGG</option>
                                    <option value="11">Arachidonic acid metabolism</option>
                                    <option value="219">Arginine and proline metabolism</option>
                                    <option value="90">Ascorbate and aldarate metabolism</option>
                                    <option value="201">ATP synthesis</option>
                                    <option value="104">Axon guidance</option>
                                    <option value="239">B cell receptor signaling pathway</option>
                                    <option value="58">Benzoate degradation via CoA ligation</option>
                                    <option value="139">beta-Alanine metabolism</option>
                                    <option value="37">Bile acid biosynthesis</option>
                                    <option value="43">Biogenic_Amine_Synthesis</option>
                                    <option value="91">Biogenic_Amine_Synthesis</option>
                                    <option value="135">Biosynthesis of ansamycins</option>
                                    <option value="72">Biosynthesis of steroids</option>
                                    <option value="120">Biosynthesis of vancomycin group antibiotics</option>
                                    <option value="123">Biotin metabolism</option>
                                    <option value="170">Bisphenol A degradation</option>
                                    <option value="56">Blood group glycolipid biosynthesis-lactoseries</option>
                                    <option value="130">Blood group glycolipid biosynthesis-neolactoseries</option>
                                    <option value="157">Blood_Clotting_Cascade</option>
                                    <option value="204">Butanoate metabolism</option>
                                    <option value="50">C21-Steroid hormone metabolism</option>
                                    <option value="147">C5-Branched dibasic acid metabolism</option>
                                    <option value="3">Calcium signaling pathway</option>
                                    <option value="233">Calcium_regulation_in_cardiac_cells</option>
                                    <option value="228">Caprolactam degradation</option>
                                    <option value="231">Carbon fixation</option>
                                    <option value="99">Cell adhesion molecules (CAMs)</option>
                                    <option value="218">Cell_cycle_KEGG</option>
                                    <option value="151">Cholesterol_Biosynthesis</option>
                                    <option value="122">Chondroitin sulfate biosynthesis</option>
                                    <option value="125">Circadian_Exercise</option>
                                    <option value="215">Citrate cycle (TCA cycle)</option>
                                    <option value="55">Complement and coagulation cascades</option>
                                    <option value="237">Complement_Activation_Classical</option>
                                    <option value="68">Cyanoamino acid metabolism</option>
                                    <option value="64">Cyanoamino acid metabolism</option>
                                    <option value="82">Cysteine metabolism</option>
                                    <option value="154">Cytokine-cytokine receptor interaction</option>
                                    <option value="128">Dentatorubropallidoluysian atrophy (DRPLA)</option>
                                    <option value="47">D-Glutamine and D-glutamate metabolism</option>
                                    <option value="114">DNA polymerase</option>
                                    <option value="207">DNA_replication_Reactome</option>
                                    <option value="169">Dorso-ventral axis formation</option>
                                    <option value="144">Eicosanoid_Synthesis</option>
                                    <option value="142">Electron_Transport_Chain</option>
                                    <option value="200">Epithelial cell signaling in Helicobacter pylori</option>
                                    <option value="158">Fatty acid biosynthesis</option>
                                    <option value="180">Fatty acid elongation in mitochondria</option>
                                    <option value="45">Fatty acid elongation in mitochondria</option>
                                    <option value="118">Fatty acid metabolism</option>
                                    <option value="87">Fatty acid metabolism</option>
                                    <option value="88">Fatty_Acid_Degradation</option>
                                    <option value="166">Fatty_Acid_Degradation</option>
                                    <option value="174">Fatty_Acid_Synthesis</option>
                                    <option value="101">Fc epsilon RI signaling pathway</option>
                                    <option value="18">Flagellar assembly</option>
                                    <option value="115">Focal adhesion</option>
                                    <option value="167">Folate biosynthesis</option>
                                    <option value="102">Fructose and mannose metabolism</option>
                                    <option value="194">G_Protein_Signaling</option>
                                    <option value="106">G1_to_S_cell_cycle_Reactome</option>
                                    <option value="214">G13_Signaling_Pathway</option>
                                    <option value="199">Galactose metabolism</option>
                                    <option value="240">gamma-Hexachlorocyclohexane degradation</option>
                                    <option value="163">Ganglioside biosynthesis</option>
                                    <option value="195">Gap junction</option>
                                    <option value="212">Globoside metabolism</option>
                                    <option value="242">Glucocorticoid_Mineralocorticoid_Metabolism</option>
                                    <option value="175">Glutamate metabolism</option>
                                    <option value="32">Glutathione metabolism</option>
                                    <option value="121">Glycan structures - biosynthesis 1</option>
                                    <option value="38">Glycan structures - biosynthesis 2</option>
                                    <option value="17">Glycan structures - degradation</option>
                                    <option value="159">Glycerolipid metabolism</option>
                                    <option value="33">Glycerophospholipid metabolism</option>
                                    <option value="8">Glycine</option>
                                    <option value="63">Glycine</option>
                                    <option value="206">Glycogen_Metabolism</option>
                                    <option value="198">Glycolysis / Gluconeogenesis</option>
                                    <option value="15">Glycolysis / Gluconeogenesis</option>
                                    <option value="13">Glycolysis_and_Gluconeogenesis</option>
                                    <option value="229">Glycolysis_and_Gluconeogenesis</option>
                                    <option value="189">Glycosaminoglycan degradation</option>
                                    <option value="44">Glycosphingolipid metabolism</option>
                                    <option value="140">Glyoxylate and dicarboxylate metabolism</option>
                                    <option value="165">GPCRDB_Class_A_Rhodopsin-like</option>
                                    <option value="224">GPCRDB_Class_A_Rhodopsin-like2</option>
                                    <option value="12">GPCRDB_Class_B_Secretin-like</option>
                                    <option value="226">GPCRDB_Class_C_Metabotropic_glutamate_pheromone</option>
                                    <option value="168">GPCRDB_Other</option>
                                    <option value="16">Hed</option>
                                    <option value="225">Hedgehog signaling pathway</option>
                                    <option value="181">Hematopoietic cell lineage</option>
                                    <option value="10">Heme_Biosynthesis</option>
                                    <option value="116">Heme_Biosynthesis</option>
                                    <option value="232">Heparan sulfate biosynthesis</option>
                                    <option value="69">High-mannose type N-glycan biosynthesis</option>
                                    <option value="73">Histidine metabolism</option>
                                    <option value="112">Huntington's disease</option>
                                    <option value="46">Hypertrophy_model</option>
                                    <option value="94">Indole and ipecac alkaloid biosynthesis</option>
                                    <option value="23">Inflammatory_Response_Pathway</option>
                                    <option value="61">Inositol metabolism</option>
                                    <option value="124">Inositol metabolism</option>
                                    <option value="26">Inositol phosphate metabolism</option>
                                    <option value="182">Insulin signaling pathway</option>
                                    <option value="152">Insulin signaling pathway</option>
                                    <option value="24">Integrin-mediated_cell_adhesion_KEGG</option>
                                    <option value="19">Irinotecan_pathway_PharmGKB</option>
                                    <option value="193">Jak-STAT signaling pathway</option>
                                    <option value="85">Keratan sulfate biosynthesis</option>
                                    <option value="220">Krebs-TCA_Cycle</option>
                                    <option value="98">Leukocyte transendothelial migration</option>
                                    <option value="79">Linoleic acid metabolism</option>
                                    <option value="9">Long-term depression</option>
                                    <option value="223">Long-term potentiation</option>
                                    <option value="25">Lysine biosynthesis</option>
                                    <option value="27">Lysine degradation</option>
                                    <option value="28">Lysine degradation</option>
                                    <option value="77">MAPK_Cascade</option>
                                    <option value="138">Matrix_Metalloproteinases</option>
                                    <option value="97">Maturity onset diabetes of the young</option>
                                    <option value="145">Metabolism of xenobiotics by cytochrome P450</option>
                                    <option value="148">Methane metabolism</option>
                                    <option value="210">Methionine metabolism</option>
                                    <option value="185">Mitochondrial_fatty_acid_betaoxidation</option>
                                    <option value="6">Mitochondrial_fatty_acid_betaoxidation</option>
                                    <option value="230">Monoamine_GPCRs</option>
                                    <option value="39">mRNA_processing_binding_Reactome</option>
                                    <option value="42">mRNA_processing_Reactome</option>
                                    <option value="236">Neuroactive ligand-receptor interaction</option>
                                    <option value="89">Neurodegenerative Disorders</option>
                                    <option value="95">N-Glycan biosynthesis</option>
                                    <option value="96">N-Glycan degradation</option>
                                    <option value="161">Nicotinate and nicotinamide metabolism</option>
                                    <option value="51">Nitrogen metabolism</option>
                                    <option value="137">Notch signaling pathway</option>
                                    <option value="132">Nuclear_Receptors</option>
                                    <option value="178">Nucleotide sugars metabolism</option>
                                    <option value="41">Nucleotide_GPCRs</option>
                                    <option value="173">Nucleotide_Metabolism</option>
                                    <option value="179">O-Glycan biosynthesis</option>
                                    <option value="133">One carbon pool by folate</option>
                                    <option value="76">Ovarian_Infertility_Genes</option>
                                    <option value="216">Oxidative phosphorylation</option>
                                    <option value="59">Pantothenate and CoA biosynthesis</option>
                                    <option value="141">Parkinson's disease</option>
                                    <option value="150">Pentose and glucuronate interconversions</option>
                                    <option value="217">Pentose phosphate pathway</option>
                                    <option value="40">Pentose_Phosphate_Pathway</option>
                                    <option value="188">Peptide_GPCRs</option>
                                    <option value="1">Peptidoglycan biosynthesis</option>
                                    <option value="92">Phenylalanine</option>
                                    <option value="186">Phenylalanine</option>
                                    <option value="129">Phenylalanine metabolism</option>
                                    <option value="20">Phenylalanine metabolism</option>
                                    <option value="67">Phosphatidylinositol signaling system</option>
                                    <option value="71">Photosynthesis</option>
                                    <option value="202">Polyketide sugar unit biosynthesis</option>
                                    <option value="117">Porphyrin and chlorophyll metabolism</option>
                                    <option value="110">Propanoate metabolism</option>
                                    <option value="78">Prostaglandin_synthesis_regulation</option>
                                    <option value="209">Proteasome</option>
                                    <option value="113">Proteasome_Degradation</option>
                                    <option value="172">Purine metabolism</option>
                                    <option value="241">Pyrimidine metabolism</option>
                                    <option value="103">Pyruvate metabolism</option>
                                    <option value="100">Reductive carboxylate cycle (CO2 fixation)</option>
                                    <option value="30">Regulation of actin cytoskeleton</option>
                                    <option value="235">Retinol metabolism</option>
                                    <option value="4">Riboflavin metabolism</option>
                                    <option value="203">Ribosomal_Proteins</option>
                                    <option value="205">RNA polymerase</option>
                                    <option value="83">RNA_transcription_Reactome</option>
                                    <option value="184">S1P_Signaling</option>
                                    <option value="164">S1P_Signaling</option>
                                    <option value="146">Selenoamino acid metabolism</option>
                                    <option value="107">Small_ligand_GPCRs</option>
                                    <option value="80">Smooth_muscle_contraction</option>
                                    <option value="156">Smooth_muscle_contraction</option>
                                    <option value="2">Starch and sucrose metabolism</option>
                                    <option value="134">Statin_Pathway_PharmGKB</option>
                                    <option value="70">Steroid_Biosynthesis</option>
                                    <option value="109">Stilbene</option>
                                    <option value="131">Streptomycin biosynthesis</option>
                                    <option value="127">Striated_muscle_contraction</option>
                                    <option value="53">Styrene degradation</option>
                                    <option value="208">Sulfur metabolism</option>
                                    <option value="192">Synthesis and degradation of ketone bodies</option>
                                    <option value="162">Synthesis_and_Degradation_of_Keton_Bodies_KEGG</option>
                                    <option value="34">Synthesis_and_Degradation_of_Keton_Bodies_KEGG</option>
                                    <option value="36">T cell receptor signaling</option>
                                    <option value="160">T cell receptor signaling pathway</option>
                                    <option value="48">Taurine and hypotaurine metabolism</option>
                                    <option value="190">Terpenoid biosynthesis</option>
                                    <option value="153">Tetrachloroethene degradation</option>
                                    <option value="74">Tetracycline biosynthesis</option>
                                    <option value="211">TGF_Beta_Signaling_Pathway</option>
                                    <option value="62">TGF-beta signaling pathway</option>
                                    <option value="21">Tigh</option>
                                    <option value="81">Tight junction</option>
                                    <option value="5">Toll-like receptor signaling pathway</option>
                                    <option value="93">Translation_Factors</option>
                                    <option value="221">Tryptophan metabolism</option>
                                    <option value="57">Two-component system - General</option>
                                    <option value="7">Type I diabetes mellitus</option>
                                    <option value="183">Type II diabetes mellitus</option>
                                    <option value="31">Type II secretion system</option>
                                    <option value="35">Type III secretion system</option>
                                    <option value="86">Tyrosine metabolism</option>
                                    <option value="105">Tyrosine metabolism</option>
                                    <option value="222">Ubiquinone biosynthesis</option>
                                    <option value="196">Ubiquitin mediated proteolysis</option>
                                    <option value="155">Urea cycle and metabolism of amino groups</option>
                                    <option value="126">Urea cycle and metabolism of amino groups</option>
                                    <option value="84">Valine</option>
                                    <option value="197">Valine</option>
                                    <option value="49">Vitamin B6 metabolism</option>
                                    <option value="66">Wnt signaling pathway</option>
                                    <option value="52">Wnt_signaling</option>
                                </select>
                            </td>
                        </tr>
                        
                        <tr>
                            <th align="left">Annotation:</th>
                            <td>
                                <select name="annotationID" id="annotationID" style="width: 200px;">
                                    <option value=""></option>
                                </select>
                            </td>
                        </tr>
                        
                    </table>
                    
                    <h2>2. Specifiy filter options</h2>
                    
                    <table>
                        <tr>
                            <th align="left">Collapsed visualization view?</th>
                            <td align="right"><input TYPE="checkbox" NAME="unique" value="YES"> <i><small>[Check for 'YES']</small></i></td>
                        </tr>
                        
                        <tr>
                            <th align="left">Exclusion Filter?</th>
                            <td align="right"><input TYPE="checkbox" NAME="exclude" value="YES"> <i><small>[Check for 'YES']</small></i></td>
                        </tr>
                    </table>
                    
                    <h2>3. Update Visualization</h2>
                    
                    <table>
                        <tr>
                            <th><input TYPE="SUBMIT" VALUE="Submit Filter"></th>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        
    </body>
    
</html>