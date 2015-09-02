package de.bo.aid.boese.homematic.xml.test;

import java.io.File;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import de.bo.aid.boese.homematic.xml.ChannelXML;
import de.bo.aid.boese.homematic.xml.ComponentXML;
import de.bo.aid.boese.homematic.xml.DeviceXML;

public class SerialisationTest {
	
	@Test
	public void test(){

		ComponentXML comp1 = new ComponentXML();
		comp1.setName("INSTALL_TEST");
		comp1.setAktor(true);
		comp1.setDescription("Schalten");
		
		ChannelXML c1 = new ChannelXML();
		c1.setNumber(1);
		c1.setComponents(Arrays.asList(comp1));

		ComponentXML comp2 = new ComponentXML();
		comp2.setName("VOLTAGE");
		comp2.setDescription("Spannung");
		comp2.setAktor(false);
		comp2.setUnit("V");
		
		ChannelXML c2 = new ChannelXML();
		c2.setNumber(2);
		c2.setComponents(Arrays.asList(comp2));
		
		DeviceXML dev = new DeviceXML();
		dev.setFirmware("1.6");
		dev.setModel("HM-ES-PMSw1-PI");
		dev.setChannels(Arrays.asList(c1, c2));
		
		try{
		JAXBContext context = JAXBContext.newInstance( DeviceXML.class );
		Marshaller m = context.createMarshaller();
		m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		
		File file = new File( "Device.xml" );
		m.marshal( dev, file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void read(){
		File file = new File( "Device.xml" );
		try {
			JAXBContext context = JAXBContext.newInstance( DeviceXML.class );
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			DeviceXML device = (DeviceXML)jaxbUnmarshaller.unmarshal(file);
			toXml(device);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//prints the XML to the console
	public void toXml(DeviceXML device) {
	    try {
	        JAXBContext ctx = JAXBContext.newInstance(device.getClass());
	        Marshaller marshaller = ctx.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        marshaller.marshal(device, System.out);
	    }
	    catch (Exception
	            e) {

	              //catch exception 
	    }
	}

}
