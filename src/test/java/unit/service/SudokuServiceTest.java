package unit.service;

import com.affinitas.Application;
import com.affinitas.constant.MoveResult;
import com.affinitas.dao.SudokuDao;
import com.affinitas.model.Move;
import com.affinitas.service.SudokuGenerateService;
import com.affinitas.service.SudokuValidateService;
import com.affinitas.utils.SudokuInvalidDataException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SudokuServiceTest {

	@Autowired
	private SudokuGenerateService generateService;
	@Autowired
	private SudokuValidateService validateService;
	@Autowired
	private SudokuDao sudokuDao;

	private String id;

	@Before
	public void setup() throws Exception {
		id = generateService.generateSudoku().getId();
	}

	@Test
	public void doSuccessMove() throws Exception {
		Move move = new Move(0, 1, 1);
		Assert.assertEquals(MoveResult.VALID, validateService.validateMove(id, move));
		int[][] data = sudokuDao.getSudokuById(id).getData();
		Assert.assertEquals(1, data[0][1]);
	}

	@Test
	public void doInvalidRowMove() throws Exception {
		Move move = new Move(0, 1, 7);
		Assert.assertEquals(MoveResult.INVALID, validateService.validateMove(id, move));
		int[][] data = sudokuDao.getSudokuById(id).getData();
		Assert.assertEquals(0, data[0][1]);
	}

	@Test
	public void doInvalidColMove() throws Exception {
		Move move = new Move(1, 0, 9);
		Assert.assertEquals(MoveResult.INVALID, validateService.validateMove(id, move));
		int[][] data = sudokuDao.getSudokuById(id).getData();
		Assert.assertEquals(0, data[1][0]);
	}

	@Test
	public void doInvalidBoxMove() throws Exception {
		Move move = new Move(0, 1, 8);
		Assert.assertEquals(MoveResult.INVALID, validateService.validateMove(id, move));
		int[][] data = sudokuDao.getSudokuById(id).getData();
		Assert.assertEquals(0, data[0][1]);
	}

	@Test
	public void doInvalidRowDataMove() throws Exception {
		Move move = new Move(100, 1, 8);
		try {
			validateService.validateMove(id, move);
			Assert.fail();
		} catch (SudokuInvalidDataException e) {
		}
	}

	@Test
	public void doInvalidColDataMove() throws Exception {
		Move move = new Move(1, 100, 8);
		try {
			validateService.validateMove(id, move);
			Assert.fail();
		} catch (SudokuInvalidDataException e) {
		}
	}

	@Test
	public void doInvalidDigitDataMove() throws Exception {
		Move move = new Move(1, 1, 100);
		try {
			validateService.validateMove(id, move);
			Assert.fail();
		} catch (SudokuInvalidDataException e) {
		}
	}
}
