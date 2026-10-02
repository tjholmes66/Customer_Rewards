package org.tomholmes.opensource.customerrewards.ws.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@WebAppConfiguration
public class CustomerRewardsControllerTest
{
    private final static String BASE_URL = "/api/v1/customers/rewards";

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;
    protected MockHttpSession session;


    @Test
    public void testMockCreateAccount() throws Exception
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).defaultRequest(post(BASE_URL)).build();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

}
