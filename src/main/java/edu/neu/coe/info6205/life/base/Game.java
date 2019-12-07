package edu.neu.coe.info6205.life.base;

import GApro.GenoType;
import edu.neu.coe.info6205.life.library.Library;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class Game implements Generational<Game, Grid>, Countable, Renderable {
                
		/**
		 * Method to get the cell count.
		 *
		 * @return the number of live cells.
                 * 
                 * 
		 */
		@Override
		public int getCount() {
				return grid.getCount();
		}

		@Override
		public String toString() {
				return "Game{" +
								"grid=" + grid +
								", generation=" + generation +
								'}';
		}

		/**
		 * Method to test equality, ignoring generation.
		 *
		 * @param o the other Game.
		 * @return true if this and o are equivalent.
		 */
		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (!(o instanceof Game)) return false;
				Game game = (Game) o;
				return grid.equals(game.grid);
		}

		/**
		 * Method to generate a hashCode, ignoring generation.
		 *
		 * @return hashCode for this.
		 */
		@Override
		public int hashCode() {
				return Objects.hash(grid);
		}

		@Override
		public Game generation(BiConsumer<Long, Grid> monitor) {
				monitor.accept(generation, grid);
				return new Game(generation + 1, grid.generation(this.monitor), this, this.monitor);
		}

		public Game(long generation, BiConsumer<Long, Group> monitor) {
				this(generation, new Grid(generation), null, monitor);
		}

		public Game(long generation) {
				this(generation, (l, g) -> {
				});
		}

		public Game() {
				this(0L);
		}

		@Override
		public String render() {
				return grid.render();
		}

		/**
		 * Get the (unique) Group belonging to the grid.
		 *
		 * @return a Group.
		 */
		public Group getGroup() {
				return grid.getGroup();
		}

		/**
		 * Method to get a very crude measure of growth rate based on this Game and the very first Game in the series.
		 *
		 * @return a double which will be zero for expired Games and 1.0 for stable games.
		 * Anything else suggests sustained growth.
		 */
		public double growthRate() {
				Game game = this;
				while (game.previous != null) {
						game = game.previous;
				}
				long growth = (long) getCount() - game.getCount();
				long generations = generation - game.generation;
				return generations > 0 ? growth * 1.0 / generations : -0.1;
		}

		public static final int MaxGenerations = 1000;

		/**
		 * Main program for Game of Life.
		 * @param args the name of the starting pattern (defaults to "Blip")
		 */
		public static void main(String[] args) {
                                GenoType genoType = new GenoType(20);                            
				String patternName = args.length > 0 ? args[0] : "ourPattern";
				System.out.println("Game of Life with starting pattern: " + patternName);
				//final String pattern = Library.get(patternName);
                                System.out.println("my pattern: "+genoType.getPetternStr());
                                final String pattern = genoType.getPetternStr();
				//final Behavior generations = run(0L, pattern);
                                Long generations = myRun(pattern);
				System.out.println("Ending Game of Life after " + generations + " generations");
		}

		/**
		 * Run the game starting with pattern.
		 *
		 * @param generation the starting generation.
		 * @param pattern    the pattern name.
		 * @return the generation at which the game expired.
		 */
                

                public static final Map<Game, Long> generations = new HashMap<>();
                public static Long myRun(String pattern) {
				final long generation = 0L;
				final Grid grid = new Grid(generation);
				grid.add(Group.create(generation, pattern));
				BiConsumer<Long, Grid> gridMonitor = (l, g) -> System.out.println("generation " + l + "; grid=" + g);
                                //print plot and count
				BiConsumer<Long, Group> groupMonitor = (l, g) -> System.out.println("generation " + l + ";\ngroup=\n" + g.render()+ "\ncount=" + g.getCount());
				Game game = new Game(generation, grid, null, groupMonitor);
				while (!game.terminated()) {
						generations.put(game, game.generation);
						game = game.generation(gridMonitor);
				}
				System.out.println("Ending Game of Life after " + game.generation + " generations and with " + game.getCount() + " cells");
				return game.generation;
		}
                
		public static Behavior run(long generation, String pattern) {
				return run(generation, pattern, MaxGenerations);
		}

		/**
		 * Run the game starting with pattern.
		 *
		 * @param generation     the starting generation.
		 * @param pattern        the pattern name.
		 * @param maxGenerations the maximum number of generations for this run.
		 * @return the generation at which the game expired.
		 */
		public static Behavior run(long generation, String pattern, int maxGenerations) {
				return run(generation, Point.points(pattern), maxGenerations);
		}

		/**
		 * Run the game starting with a list of points.
		 *
		 * @param generation the starting generation.
		 * @param points     a list of points in Grid coordinates.
		 * @param maxGenerations the maximum number of generations for this run.
		 * @return the generation at which the game expired.
		 */
		public static Behavior run(long generation, List<Point> points, int maxGenerations) {
				return run(create(generation, points), (l, g) -> System.out.println("generation " + l + "; grid=" + g), maxGenerations);
		}

		/**
		 * Factory method to create a new Game starting at the given generation and with the given points.
		 * @param generation the starting generation.
		 * @param points a list of points in Grid coordinates.
		 * @return a Game.
		 */
		public static Game create(long generation, List<Point> points) {
				final Grid grid = new Grid(generation);
				grid.add(Group.create(generation, points));
				BiConsumer<Long, Group> groupMonitor = (l, g) -> System.out.println("generation " + l + ";\ncount=" + g.getCount());
				return new Game(generation, grid, null, groupMonitor);
		}

		/**
		 * Method to run a Game given a monitor method for Grids.
		 *
		 * @param game        the game to run.
		 * @param gridMonitor the monitor
		 * @param maxGenerations the maximum number of generations for this run.
		 * @return the generation when expired.
		 */
		public static Behavior run(Game game, BiConsumer<Long, Grid> gridMonitor, int maxGenerations) {
				if (game == null) throw new LifeException("run: game must not be null");
				Game g = game;
				while (!g.terminated()) g = g.generation(gridMonitor);
				int reason = g.generation >= maxGenerations ? 2 : g.getCount() <= 1 ? 0 : 1;
				return new Behavior(g.generation, g.growthRate(), reason);
		}

		/**
		 * Class to model the behavior of a game of life.
		 */
		public static class Behavior {
				/**
				 * The generation at which the run stopped.
				 */
				public final long generation;
				/**
				 * The average rate of growth.
				 */
				public final double growth;
				/**
				 * The reason the run stopped:
				 * 0: the cells went extinct
				 * 1: a repeating sequence was noted;
				 * 2: the maximum configured number of generations was reached.
				 */
				private final int reason;

				public Behavior(long generation, double growth, int reason) {
						this.generation = generation;
						this.growth = growth;
						this.reason = reason;
				}

				public double evaluate() {
						switch (reason) {
								case 1: return 0;
								case 2: return growth;
								default: return -1;
						}
				}

				@Override
				public String toString() {
						return "Behavior{" +
										"generation=" + generation +
										", growth=" + growth +
										", reason=" + reason +
										'}';
				}

				@Override
				public boolean equals(Object o) {
						if (this == o) return true;
						if (!(o instanceof Behavior)) return false;
						Behavior behavior = (Behavior) o;
						return generation == behavior.generation &&
										Double.compare(behavior.growth, growth) == 0 &&
										reason == behavior.reason;
				}

				@Override
				public int hashCode() {
						return Objects.hash(generation, growth, reason);
				}
		}

		private Game(long generation, Grid grid, Game previous, BiConsumer<Long, Group> monitor) {
				this.grid = grid;
				this.generation = generation;
				this.previous = previous;
				this.monitor = monitor;
		}

		private boolean terminated() {
				return testTerminationPredicate(g -> g.generation >= MaxGenerations, "having exceeded " + MaxGenerations + " generations") ||
                                       testTerminationPredicate(g -> g.getCount() <= 1, "extinction"); 
								// TODO now we look for two consecutive equivalent games...
                                                                ////don't end if match cycle    
				       //testTerminationPredicate(Game::previousMatchingCycle, "having matching previous games");
		}

		/**
		 * Check to see if there is a previous pair of matching games.
		 * <p>
		 * NOTE this method of checking for cycles is not guaranteed to work!
		 * NOTE this method may be very inefficient.
		 * <p>
		 * TODO project teams may need to fix this method.
		 *
		 * @param game the game to check.
		 * @return a Boolean.
		 */
		private static boolean previousMatchingCycle(Game game) {
				MatchingGame matchingGame = findCycle(game);
				int cycles = matchingGame.k;
				Game history = matchingGame.match;
				if (history == null) return false;
				Game current = game;
				while (current != null && history != null && cycles > 0) {
						if (current.equals(history)) {
								current = current.previous;
								history = history.previous;
								cycles--;
						} else
								return false;
				}
                                
				return true;
		}

		/**
		 * Find a game which matches the given game.
		 *
		 * @param game the game to match.
		 * @return a MatchingGame object: if the match field is null it means that we did not find a match;
		 * the k field is the number of generations between game and the matching game.
		 */
		private static MatchingGame findCycle(Game game) {
				int k = 1;
				Game candidate = game.previous;
				while (candidate != null && !game.equals(candidate)) {
						candidate = candidate.previous;
						k++;
				}
				return new MatchingGame(k, candidate);
		}

		private static class MatchingGame {
				private final int k;
				private final Game match;

				public MatchingGame(int k, Game match) {
						this.k = k;
						this.match = match;
				}
		}

		private boolean testTerminationPredicate(Predicate<Game> predicate, String message) {
				if (predicate.test(this)) {
						System.out.println("Terminating due to: " + message);
						return true;
				}
				return false;
		}
                private final Grid grid;
		private final Game previous;
		private final BiConsumer<Long, Group> monitor;
		private final long generation;
		
}
