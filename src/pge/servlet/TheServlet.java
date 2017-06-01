package pge.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class TheServlet
 */
@WebServlet("/TheServlet")
public class TheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TheServlet() {
        super();
    }
    private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE 	= 1024 * 1024 * 3; 	// 3MB
	private static final int MAX_FILE_SIZE 		= 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 50; // 50MB

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//GETPARAMETER WONT WORK BECAUSE ENCTYPE="MULTIPART/FORM-DATA"
		
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer1 = response.getWriter();
			writer1.println("Request does not contain upload data");
			writer1.flush();
			return;
		}
		
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
			+ File.separator + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
			System.out.println("Directory didn't exist");
		}
		String usageFilePath = "";
		String billingFilePath = "";
		String baselineTerritory = "";
		String heatSource = "";
		double weekdayDistance = 0;
		double weekendDistance = 0;
		double chargingLevel = 0;
		int chargeStartTime = 0;
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(request);
			Iterator<FileItem> iter = formItems.iterator();

			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if(item.isFormField())// processes only fields that are form fields
				{
					String name = item.getFieldName();
					String value = item.getString();
					if(name.equals("baselineTerritory"))
					{
						baselineTerritory = value;
					}
					else if (name.equals("heatSource"))
					{
						heatSource = value;
					}
					else if (name.equals("weekdayDistance"))
					{
						weekdayDistance = Double.parseDouble(value);
					}
					else if (name.equals("weekendDistance"))
					{
						weekendDistance = Double.parseDouble(value);
					}
					else if (name.equals("chargingLevel"))
					{
						chargingLevel = Double.parseDouble(value);
					}
					else if (name.equals("chargeStartTime"))
					{
						chargeStartTime = Integer.parseInt(value);
					}
				}
				else//Not a form field
				{
					String nameOfFile = item.getName();
					if(nameOfFile.endsWith(".csv"))
					{
						String fileName = new File("bill_periods.csv").getName();
						billingFilePath = uploadPath + File.separator + fileName;
						File storeFile = new File(billingFilePath);
						
						// saves the file on disk
						item.write(storeFile);
					}
					else if(nameOfFile.endsWith(".xml"))
					{
						String usageFilename = new File("interval_usage.xml").getName();
						usageFilePath = uploadPath + File.separator + usageFilename;
						File storeFile = new File(usageFilePath);
						
						// saves the file on disk
						item.write(storeFile);
					}
					else
					{
						System.out.println("Incorrect File Type");
					}
				}
			}
			
			request.setAttribute("message", "Upload has been done successfully!");
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
			System.out.println("Error");
		}
		
		response.setCharacterEncoding("utf-8");
		 
		
		//Returns an array
		//E1: total, tier1, tier2, tier3, tier4
		//E6: total, tier1, tier2, tier3, tier4, off, part, peak
		//EV: total, off, part, peak
		//17 components in the array, in the above order
		double[] dataArray = com.pge.rateCompare.Compare.compare(usageFilePath, billingFilePath, baselineTerritory, heatSource, chargingLevel, weekdayDistance, weekendDistance, chargeStartTime);
		double e1Total = dataArray[0];
		double e1T1 = dataArray[1];
		double e1T2 = dataArray[2];
		double e1T3 = dataArray[3];
		double e1T4 = dataArray[4];
		double e6Total = dataArray[5];
		double e6T1 = dataArray[6];
		double e6T2 = dataArray[7];
		double e6T3 = dataArray[8];
		double e6T4 = dataArray[9];
		double e6Off = dataArray[10];
		double e6Part = dataArray[11];
		double e6Peak = dataArray[12];
		double evTotal = dataArray[13];
		double evOff = dataArray[14];
		double evPart = dataArray[15];
		double evPeak = dataArray[16];
		PrintWriter writer = response.getWriter();
		StringBuffer res = new StringBuffer();
		
		//Writing JSON Data
	    res.append("{\"d\":[{\"E1\":{\"total\":");
	    res.append(e1Total);
	    res.append(", \"tiers\":{\"t1\":");
	    res.append(e1T1);
	    res.append(",\"t2\":");
	    res.append(e1T2);
	    res.append(",\"t3\":");
	    res.append(e1T3);
	    res.append(",\"t4\":");
	    res.append(e1T4);
	    res.append("}},");
	    
	    res.append("\"E6\":{\"total\":");
	    res.append(e6Total);
	    res.append(", \"tiers\":{\"t1\":");
	    res.append(e6T1);
	    res.append(",\"t2\":");
	    res.append(e6T2);
	    res.append(",\"t3\":");
	    res.append(e6T3);
	    res.append(",\"t4\":");
	    res.append(e6T4);
	    res.append("},");
	    res.append("\"peaks\":{\"off\":");
	    res.append(e6Off);
	    res.append(",\"part\":");
	    res.append(e6Part);
	    res.append(",\"peak\":");
	    res.append(e6Peak);
	    res.append("}},");
	    
	    res.append("\"EV\":{\"total\":");
	    res.append(evTotal);
	    res.append(",\"peaks\":{\"off\":");
	    res.append(evOff);
	    res.append(",\"part\":");
	    res.append(evPart);
	    res.append(",\"peak\":");
	    res.append(evPeak);
	    res.append("}}");
	    
	    res.append("}]}");
	    writer.println(res.toString());
	}

}
