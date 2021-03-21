package com.simondudley.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(UserConfiguration.class)
public class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void unprotectedEndpoint() throws Exception {

		this.mockMvc.perform(get("/unprotected")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("This is an unprotected endpoint")));
	}

	@Test
	public void protectedEndpointNoAuth() throws Exception {
		this.mockMvc.perform(get("/protected")).andDo(print()).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "basicUser", password = "basicPassword")
	public void protectedEndpointWithBasicAuth() throws Exception {

		this.mockMvc.perform(get("/protected")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("This is a protected endpoint")))
				.andExpect(content().string(containsString("basicUser")));
	}

	@Test
	public void protectedEndpointWithSSO() throws Exception {

		this.mockMvc.perform(get("/protected").header("X-Auth-User", "ssoUser")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("This is a protected endpoint")))
				.andExpect(content().string(containsString("ssoUser")));
	}

	@Test
	public void protectedEndpointWithSSOAndBasicAuth() throws Exception {

		this.mockMvc.perform(get("/protected").header("X-Auth-User", "ssoUser").with(httpBasic("basicUser", "basicPassword"))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("This is a protected endpoint")))
				.andExpect(content().string(containsString("ssoUser")));
	}
}