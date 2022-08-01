package com.parking.service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.parking.model.Ticket;
import com.parking.repository.TicketRepository;

@Component
public class TicketService {

	private static final int totalSlots = 6;

	@PostConstruct
	public void init() {
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				StartTicketingProcess();
			}
		}, 5000);
	}

	@Autowired
	TicketRepository repository;

	public void StartTicketingProcess() {

		String input;
		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** WELCOME TO PARK MY CAR ***********");
		System.out.println("Commands to use this system are give below.");
		System.out.println("park -> Use this to park the Vehicle.");
		System.out.println("leave -> Use this to exit the vehicle and free the slot.");
		System.out.println("status -> To get the details of parking lot.");
		System.out.println(
				"registration_numbers_for_cars_with_colour -> To get registration number of cars with specified color.");
		System.out.println("slot_numbers_for_cars_with_colour -> To get slot number of cars with specified color.");
		System.out.println("slot_number_for_registration_number -> To get slot number by registration number.");
		System.out.println("exit -> To exit the system.\n");
		System.out.println(
				"--------------------------------------------------------------------------------------------------\n");

		while (!(input = scanner.nextLine()).startsWith("exit")) {
			try {
				String command = input.split(" ")[0];
				switch (command) {
				case "park":
					System.out.println(parkTheCar(input));
					break;

				case "leave":
					System.out.println(leaveParking(input));
					break;

				case "status":
					System.out.println(fetchStatus());
					break;

				case "registration_numbers_for_cars_with_colour":
					System.out.println(carNumberByColor(input));
					break;

				case "slot_numbers_for_cars_with_colour":
					System.out.println(slotNumberByColor(input));
					break;

				case "slot_number_for_registration_number":
					System.out.println(slotNumberByCarNumber(input));
					break;

				default:
					System.out.println("Invalid Command!!!");
					break;
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Invalid Command!!!");
			}
		}

		{
			System.out.println("GoodBye!");
			System.exit(1);
		}
	}

	@Transactional
	private String parkTheCar(String input) {

		String number = input.split(" ")[1];
		String color = input.split(" ")[2];
		int slot = 0;

		List<Integer> parkedSlots = repository.findByStatus("Parked").stream().map(Ticket::getSlot)
				.collect(Collectors.toList());

		for (int i = 1; i <= totalSlots; i++) {
			if (!parkedSlots.contains(i)) {
				slot = i;
				break;
			}
		}

		if (slot == 0) {
			return "Sorry, parking lot is full.";
		} else {
			Ticket ticket = new Ticket(number, color, slot, "Parked");
			repository.save(ticket);
			return "Allocated slot number: " + slot;
		}

	}

	@Transactional
	private String leaveParking(String input) {

		String slot = input.split(" ")[1];
		repository.emptyTheSlot(Integer.parseInt(slot));
		return "Slot number " + slot + " is free";

	}

	private String fetchStatus() {

		List<Ticket> ticketList = repository.findByStatus("Parked");
		if (ticketList.isEmpty()) {
			return "No data available";
		} else {
			String data = "No. --- Registration No. -------- Color \n";
			for (Ticket t : ticketList) {
				data += t.getSlot() + " --- " + t.getCarNumber() + " -------- " + t.getCarColor() + " \n";
			}
			return data;
		}
	}

	private String carNumberByColor(String input) {

		String color = input.split(" ")[1];
		List<String> carNumbers = repository.getCarNumberByColor(color);
		return carNumbers.toString();
		
	}

	private String slotNumberByColor(String input) {
		
		String color = input.split(" ")[1];
		List<Integer> slots = repository.getSlotByColor(color);
		return slots.toString();
	
	}

	private String slotNumberByCarNumber(String input) {

		String carNumber = input.split(" ")[1];
		List<Integer> slots = repository.getSlotByCarNumber(carNumber);
		return slots.toString();
	}

}
