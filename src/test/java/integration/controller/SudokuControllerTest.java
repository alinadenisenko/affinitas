package integration.controller;

import com.affinitas.Application;
import com.affinitas.constant.MoveResult;
import com.affinitas.model.Move;
import com.affinitas.service.SudokuGenerateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SudokuControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SudokuGenerateService generateService;

	private String id;

	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(wac).build();
		id = generateService.generateSudoku().getId();
	}

	@Test
	public void getSudokuSuccess() throws Exception {
		mockMvc.perform(get("/sudoku/generate"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.data", notNullValue()));
	}

	@Test
	public void moveSudokuNotFound() throws Exception {
		mockMvc.perform(post("/sudoku/validate_move/no" + id)
				.contentType(contentType)
				.content(mapper.writeValueAsString(new Move(1, 2, 1))))
				.andExpect(status().isNotFound());
	}

	@Test
	public void moveSudokuInvalidData() throws Exception {
		mockMvc.perform(post("/sudoku/validate_move/" + id)
				.contentType(contentType)
				.content(mapper.writeValueAsString(new Move(1, 2, 100))))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void doSuccessMove() throws Exception {
		mockMvc.perform(post("/sudoku/validate_move/" + id)
				.contentType(contentType)
				.content(mapper.writeValueAsString(new Move(1, 2, 1))))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.result", is(MoveResult.VALID.toString())));
	}

	@Test
	public void doInvalidMove() throws Exception {
		mockMvc.perform(post("/sudoku/validate_move/" + id)
				.contentType(contentType)
				.content(mapper.writeValueAsString(new Move(1, 2, 7))))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.result", is(MoveResult.INVALID.toString())));
	}
}
