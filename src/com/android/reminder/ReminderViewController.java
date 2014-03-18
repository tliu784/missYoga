package com.android.reminder;

public class ReminderViewController {
	

	private MedReminderModel reminder;
	private titleSection titleSec;
	private detailSection detailSec;
	private editSection editSec;
	
		
	public ReminderViewController(MedReminderModel reminder) {
		this.reminder = reminder;
		//call 3 constructors
		//call 3 set listeners
	}

	class titleSection{
		titleSection(){
			//constructor: new all views
		}
		
		void setListeners(){
			//set all listeners
		}
	}
	
	class detailSection{
		
	}
	
	class editSection {
		
	}
}
