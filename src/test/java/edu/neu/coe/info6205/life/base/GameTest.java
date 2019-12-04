package edu.neu.coe.info6205.life.base;

import edu.neu.coe.info6205.life.library.Library;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;

public class GameTest {

		@Test
		public void testRunBlip() {
				String patternName = "Blip";
				System.out.println("Game of Life with starting pattern: " + patternName);
				final String pattern = Library.get(patternName);
				final Game.Behavior generations = Game.run(0L, pattern);
				assertEquals(0, generations.generation);
		}

		@Test
		public void testRunBlinker() {
				String patternName = "Blinker";
				System.out.println("Game of Life with starting pattern: " + patternName);
				final String pattern = Library.get(patternName);
				final Game.Behavior generations = Game.run(0L, pattern);
				assertEquals(new Game.Behavior(2, 0, 1), generations);
		}

		@Test
		public void generation() {
				String patternName = "Blinker";
				final String pattern = Library.get(patternName);
				final Game game = Game.create(0L, Point.points(pattern));
				Result result = new Result();
				BiConsumer<Long,Grid> monitor = (aLong, grid) -> {
						result.gen = aLong;
						result.grid = grid;
				};
				final Game nextGeneration = game.generation(monitor);
				assertEquals(3, nextGeneration.getCount());
				assertEquals(0, result.gen);
				assertEquals("O\n" +
								"*\n" +
								"*\n" +
								"Origin: {0, 0}\n", result.grid.render());
		}

		class Result {
				long gen;
				Grid grid;
		}
}