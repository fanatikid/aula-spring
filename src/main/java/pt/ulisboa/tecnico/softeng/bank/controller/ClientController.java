package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.BankDto;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientController {
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String clientForm(Model model, @PathVariable String code) {
		logger.info("clientForm bankCode:{}", code);

		Bank bankData = Bank.getBankByCode(code);

		if (bankData == null) {
			model.addAttribute("error", "Error: it does not exist a bank with the code " + code);
			model.addAttribute("bank", new BankDto());
			model.addAttribute("banks", Bank.banks);
			return "banks";
		}

		model.addAttribute("client", new ClientDto());
		model.addAttribute("bank", bankData);
		return "clients";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String clientSubmit(Model model, @PathVariable String code, @ModelAttribute ClientDto client) {
		logger.info("clientSubmit bankCode:{}, clientName:{}", code, client.getName());

		try {
			Client client2add = new Client(
					Bank.getBankByCode(code), 
					Bank.getBankByCode(code).getCode() + Bank.getBankByCode(code).getClients().size(), 
					client.getName(), 
					client.getAge()); 
			Bank.getBankByCode(code).addClient(client2add);
					
			logger.info("clientSubmit name:{}, ID:{}", client2add.getBank().getCode(), client2add.getId());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the client");
			model.addAttribute("client", client);
			model.addAttribute("bank", Bank.getBankByCode(code));
			return "clients";
		}

		return "redirect:/banks/bank/" + code;
}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public String showClient(Model model, @PathVariable String code, @PathVariable String id) {
		logger.info("showBank  bankCode:{}, clientId:{}", code, id);

		
		for (Client client : Bank.getBankByCode(code).getClients()) {
			logger.info("searching...");
			if (client.getId().equals(id)) {
				logger.info("client Found! Name:{}, clientId:{}", client.getName(), client.getId());
				model.addAttribute("client", client);
				return "clientView";
			}
		}
		
		return "clientView";
}
}
