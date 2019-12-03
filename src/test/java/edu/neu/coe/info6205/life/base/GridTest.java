package edu.neu.coe.info6205.life.base;

import edu.neu.coe.info6205.life.library.Library;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static edu.neu.coe.info6205.life.library.Library.Glider1;
import static edu.neu.coe.info6205.life.library.Library.Glider2;
import static org.junit.Assert.assertEquals;

public class GridTest {

		@Test
		public void testAdd() throws NoSuchFieldException, IllegalAccessException {
				final Grid target = new Grid(0L);
				final Group glider1 = Group.create(0L, Glider1);
				target.add(glider1);
				final Field groups = target.getClass().getDeclaredField("groups");
				groups.setAccessible(true);
				final List<Group> x = (List<Group>) groups.get(target);
				assertEquals(1, x.size());
				assertEquals(glider1, x.get(0));
		}

		@Test
		public void testGetCount() {
				final Group glider1 = Group.create(0L, Glider1);
				int count = glider1.getCount();
				final Grid target = new Grid(0L);
				target.add(glider1);
				assertEquals(count, target.getCount());
		}

		@Test(expected = UnsupportedOperationException.class)
		public void testRemove() throws NoSuchFieldException, IllegalAccessException {
				final Grid target = new Grid(0L);
				final Group glider1 = Group.create(0L, Glider1);
				target.add(glider1);
				target.remove(glider1);
		}

		@Test
		public void testForEach() {
				final Grid target = new Grid(0L);
				final Group glider1 = Group.create(0L, Glider1);
				target.add(glider1);

				Consumer<? super Group> action = (Consumer<Group>) group -> assertEquals(glider1, group);
				target.forEach(action);
		}

		@Test
		public void testGeneration() {
				final Grid target = new Grid(0L);
				final Group glider1 = Group.create(0L, Glider1);
				target.add(glider1);
				BiConsumer<Long,Void> monitor = (aLong, x) -> {
						assertEquals(0L, aLong.longValue());
				};
				final Group nextGeneration = glider1.generation(monitor);
				assertEquals(5, nextGeneration.getCount());
		}

		@Test
		public void testMergeGroups() {
				Group glider1 = Group.create(0L, Glider1);
				Group glider2 = Group.create(0L, Glider2);
				List<Group> groups = new ArrayList<>();
				groups.add(glider1);
				groups.add(glider2);
				List<Group> target = Grid.mergeGroups(groups);
				assertEquals(1, target.size());
				System.out.println(target.get(0));
		}

		@Test
		public void render() {
				final Grid target = new Grid(0L);
				target.add(Group.create(0L, Glider1));
				assertEquals("O**\n" + "..*\n" + ".*.\n" + "Origin: {0, 0}\n", target.render());
		}
}