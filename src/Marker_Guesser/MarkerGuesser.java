package Marker_Guesser;


import ij.plugin.Duplicator;
import ij.plugin.Macro_Runner;
import ij.plugin.PlugIn;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.Macro;
import ij.gui.*;





public class MarkerGuesser implements PlugIn {
	public String dir_path;
	public String[] marker_names;
	public int numImages;
	public void run(String arg) {
		 //run snt

		
		Macro_Runner m1 = new Macro_Runner();
		m1.runMacro("ojShowImage(1);run(\"Duplicate...\", \"duplicate\");", "");
		ImagePlus imageStack1 = WindowManager.getCurrentImage(); //you might want to change the imageStacks to an array so you don't only have the ability to use 2


		
		
		numImages = Options.getNImages();
		marker_names = Options.getMarkersforChannels(imageStack1);
		
		
		
		int dilate_iter = Options.getDilateIter();
		Mask_and_Filter filter_model = new Mask_and_Filter(imageStack1, dilate_iter);
		
		imageStack1 = filter_model.run("model");
		//future - Add warning if the directory path is different for second imageStack
		dir_path = filter_model.getDirPath();		
		
		
		//temporary
		double[][] transform = {
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1}
		};

		
		
		String results_path = filter_model.getResultsDir()[0];
		ObjJ_Markers om_model = new ObjJ_Markers(transform, results_path, marker_names[1]);//marker_names[0] = null because thats the cell fill channel
		om_model.run();
		
		results_path = filter_model.getResultsDir()[1];
		om_model = new ObjJ_Markers(transform, results_path, marker_names[2]);
		om_model.run();
		
		
		//for the future:
				//create loop to loop through all channels
		/*
		//add path to c++ program, you will have to compile from vs and put it in the project folder
		String exec_command = dir_path+"";
		new Macro_Runner().runMacro("exec(\"help\")", "");
		*/
		
	}
}