package accounts.web;

import accounts.AccountManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.money.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

// TODO-06: Get yourself familiarized with various testing utility classes
// - Uncomment the import statements below
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO-07: Replace @ExtendWith(SpringExtension.class) with the following annotation
// - @WebMvcTest(AccountController.class) // includes @ExtendWith(SpringExtension.class)
//@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerBootTests {

	// TODO-08: Autowire MockMvc bean
	@Autowired
	MockMvc mockMvc;

	// TODO-09: Create AccountManager mock bean using @MockBean annotation
	@MockBean
	AccountManager accountManager;

	// TODO-10: Write positive test for GET request for an account
	// - Uncomment the code and run the test and verify it succeeds
	@Test
	public void accountDetails() throws Exception {

		given(accountManager.getAccount(0L))
				.willReturn(new Account("1234567890", "John Doe"));

		mockMvc.perform(get("/accounts/0"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("name").value("John Doe"))
			   .andExpect(jsonPath("number").value("1234567890"));

		verify(accountManager).getAccount(0L);

	}

	// TODO-11: Write negative test for GET request for a non-existent account
	// - Uncomment the "given" and "verify" statements
	// - Write code between the "given" and "verify" statements
	// - Run the test and verify it succeeds
	@Test
	public void accountDetailsFail() throws Exception {

		given(accountManager.getAccount(any(Long.class)))
				.willThrow(new IllegalArgumentException("No such account with id " + 0L));

//		 (Write code here)
//		 - Use mockMvc to perform HTTP Get operation using "/accounts/9999"
//           as a non-existent account URL
//		 - Verify that the HTTP response status is 404
		mockMvc.perform(get("/accounts/9999"))
			.andExpect(status().isNotFound());

		verify(accountManager).getAccount(any(Long.class));

	}

    // TODO-12: Write test for `POST` request for an account
	// - Uncomment Java code below
	// - Write code between the "given" and "verify" statements
	// - Run the test and verify it succeeds
	@Test
	public void createAccount() throws Exception {

		Account testAccount = new Account("1234512345", "Mary Jones");
		testAccount.setEntityId(21L);

		given(accountManager.save(any(Account.class)))
				.willReturn(testAccount);

		// (Write code here)
		// Use mockMvc to perform HTTP Post operation to "/accounts"
		// - Set the request content type to APPLICATION_JSON
		// - Set the request content with Json string of the "testAccount"
		//   (Use "asJsonString" method below to convert the "testAccount"
		//   object into Json string)
		// - Verify that the response status is 201
		// - Verify that the response "Location" header contains "http://localhost/accounts/21"
		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testAccount)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "http://localhost/accounts/21"));

		verify(accountManager).save(any(Account.class));

	}

    // Utility class for converting an object into JSON string
	protected static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO-13 (Optional): Experiment with @MockBean vs @Mock
	// - Change `@MockBean` to `@Mock` for the `AccountManager dependency above
	// - Run the test and observe a test failure
	// - Change it back to `@MockBean`

	// Extra Part 1
	@Test
	public void getAllAccounts() throws Exception {

		List<Account> testAccounts = Arrays.asList(new Account("123456789", "John Doe"));
		given(accountManager.getAllAccounts())
			.willReturn(testAccounts);

		mockMvc.perform(get("/accounts"))
			.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				jsonPath("$..name").value("John Doe"),
				jsonPath("$..number").value("123456789")
			);

		verify(accountManager).getAllAccounts();
	}

	@Test
	public void getBeneficiary() throws Exception {
		Account account = new Account("1234567890", "John Doe");
		Beneficiary beneficiary = new Beneficiary("Pascal", new Percentage(0.5));
		account.addBeneficiary(beneficiary.getName(), beneficiary.getAllocationPercentage());

		given(accountManager.getAccount(anyLong()))
			.willReturn(account);

		mockMvc.perform(get("/accounts/{accountId}/beneficiaries/{beneficiaryName}", 0L, beneficiary.getName()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("name").value(beneficiary.getName()))
			.andExpect(jsonPath("allocationPercentage").value(beneficiary.getAllocationPercentage().asDouble()));

		verify(accountManager).getAccount(anyLong());
	}

	@Test
	public void getNonExistentBeneficiary() throws Exception {
		Account account = new Account("1234567890", "John Doe");

		given(accountManager.getAccount(0L))
			.willReturn(account);

		mockMvc.perform(get("/accounts/{accountId}/beneficiaries/{beneficiaryName}", 0L, "NON_EXISTENT"))
			.andExpect(status().isNotFound());

		verify(accountManager).getAccount(0L);
	}

	@Test
	public void addNewBeneficiary() throws Exception {
		Beneficiary beneficiary = new Beneficiary("Pascal", new Percentage(0.5));

		mockMvc.perform(post("/accounts/{accountId}/beneficiaries", 0L).content(beneficiary.getName()))
			.andExpectAll(
				status().isCreated(),
				header().string("Location", "http://localhost/accounts/0/beneficiaries/" + beneficiary.getName())
			);
	}

	@Test
	public void removeBeneficiary() throws Exception {
		Account account = new Account("1234567890", "John Doe");
		account.setEntityId(0L);
		Beneficiary beneficiary = new Beneficiary("Pascal", new Percentage(0.5));
		account.addBeneficiary(beneficiary.getName(), beneficiary.getAllocationPercentage());

		given(accountManager.getAccount(anyLong())).willReturn(account);

		mockMvc.perform(delete("/accounts/{accountId}/beneficiaries/{beneficiaryName}", account.getEntityId(), beneficiary.getName()))
			.andExpectAll(
				status().isNoContent()
			);

		verify(accountManager).getAccount(anyLong());

	}

	@Test
	public void removeNonExistentBeneficiary() throws Exception {
		Account account = new Account("1234567890", "John Doe");
		account.setEntityId(0L);

		given(accountManager.getAccount(anyLong())).willReturn(account);

		mockMvc.perform(delete("/accounts/{accountId}/beneficiaries/{beneficiaryName}", account.getEntityId(), "NON_EXISTENT"))
			.andExpectAll(
				status().isNotFound()
			);

		verify(accountManager).getAccount(anyLong());

	}

}
