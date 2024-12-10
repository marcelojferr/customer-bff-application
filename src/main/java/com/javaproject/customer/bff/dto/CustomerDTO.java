package com.javaproject.customer.bff.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public record CustomerDTO (
	Long id,
	String customerName,
	String customerFantasyName,
	String customerNumber,
	SegmentDTO segment,
    AddressDTO address,
	Set<ContactDTO> contact,
	CustomerStatus customerStatus
	) {
		public CustomerDTO() {this(null, "", "", "", new SegmentDTO(), new AddressDTO(), new HashSet<>(), new CustomerStatus());}

	public record SegmentDTO(
		String segmentDescription,
		String segmentPercentualValue) 
	{
		public SegmentDTO() {this("", "");}
	}

	public record AddressDTO(
		String streetAddress,
		String numberAddress,
		String districtAddress,
		String cityAddress,
		String stateAddress,
		String zipAddress) 
	{
		public AddressDTO() {this("", "", "", "", "", "");}
	}

	public record ContactDTO(
		String contactNumber,
		String contactResponsable,
		String contactZone) 
	{
		public ContactDTO() {this("", "", "");}
	}
	
	public record CustomerStatus(
			String codigo,
			String descricao) 
		{
			public CustomerStatus() {this("", "");}
		}
}