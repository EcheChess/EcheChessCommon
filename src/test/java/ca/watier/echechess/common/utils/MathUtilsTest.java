/*
 *    Copyright 2014 - 2018 Yannick Watier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ca.watier.echechess.common.utils;


import ca.watier.echechess.common.enums.CasePosition;
import ca.watier.echechess.common.enums.Direction;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by yannick on 4/25/2017.
 */
public class MathUtilsTest {

    public static final float KNIGHT_RADIUS_EQUATION = 2.23606797749979f;
    private static final Direction NORTH = Direction.NORTH;
    private static final Direction NORTH_WEST = Direction.NORTH_WEST;
    private static final Direction WEST = Direction.WEST;
    private static final Direction SOUTH_WEST = Direction.SOUTH_WEST;
    private static final Direction SOUTH = Direction.SOUTH;
    private static final Direction SOUTH_EAST = Direction.SOUTH_EAST;
    private static final Direction EAST = Direction.EAST;
    private static final Direction NORTH_EAST = Direction.NORTH_EAST;
    private static final CasePosition D_5 = CasePosition.D5;
    private static final float DELTA_SLOPE_TEST = 0f;

    @Test
    public void isPositionVertical() {
        Assert.assertTrue(MathUtils.isPositionVertical(CasePosition.A8, CasePosition.A1));
        Assert.assertTrue(MathUtils.isPositionVertical(CasePosition.A1, CasePosition.A8));
        Assert.assertTrue(MathUtils.isPositionVertical(CasePosition.A4, CasePosition.A6));
        Assert.assertFalse(MathUtils.isPositionVertical(CasePosition.A4, CasePosition.B4));
    }

    @Test
    public void isPositionHorizontal() {
        Assert.assertTrue(MathUtils.isPositionHorizontal(CasePosition.B1, CasePosition.A1));
        Assert.assertTrue(MathUtils.isPositionHorizontal(CasePosition.A8, CasePosition.B8));
        Assert.assertTrue(MathUtils.isPositionHorizontal(CasePosition.A6, CasePosition.B6));
        Assert.assertFalse(MathUtils.isPositionHorizontal(CasePosition.A4, CasePosition.A2));
    }

    @Test
    public void getPositionsBetweenTwoPosition() {
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.A1, CasePosition.H8)).containsOnly(CasePosition.B2, CasePosition.C3, CasePosition.D4, CasePosition.E5, CasePosition.F6, CasePosition.G7);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.H8, CasePosition.A1)).containsOnly(CasePosition.B2, CasePosition.C3, CasePosition.D4, CasePosition.E5, CasePosition.F6, CasePosition.G7);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.E1, CasePosition.E8)).containsOnly(CasePosition.E2, CasePosition.E3, CasePosition.E4, CasePosition.E5, CasePosition.E6, CasePosition.E7);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.E8, CasePosition.E1)).containsOnly(CasePosition.E2, CasePosition.E3, CasePosition.E4, CasePosition.E5, CasePosition.E6, CasePosition.E7);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.A4, CasePosition.H4)).containsOnly(CasePosition.B4, CasePosition.C4, CasePosition.D4, CasePosition.E4, CasePosition.F4, CasePosition.G4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.H4, CasePosition.A4)).containsOnly(CasePosition.B4, CasePosition.C4, CasePosition.D4, CasePosition.E4, CasePosition.F4, CasePosition.G4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.A8, CasePosition.H1)).containsOnly(CasePosition.B7, CasePosition.C6, CasePosition.D5, CasePosition.E4, CasePosition.F3, CasePosition.G2);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.H1, CasePosition.A8)).containsOnly(CasePosition.B7, CasePosition.C6, CasePosition.D5, CasePosition.E4, CasePosition.F3, CasePosition.G2);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.E4, CasePosition.G4)).containsOnly(CasePosition.F4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.F3, CasePosition.F5)).containsOnly(CasePosition.F4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.E3, CasePosition.G5)).containsOnly(CasePosition.F4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.G5, CasePosition.E3)).containsOnly(CasePosition.F4);
        Assertions.assertThat(MathUtils.getPositionsBetweenTwoPosition(CasePosition.A4, CasePosition.H8)).isEmpty();
    }

    @Test
    public void isPositionOnCirclePerimeter_knight() {

        int x = D_5.getX();
        int y = D_5.getY();

        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.B4, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.B6, x + KNIGHT_RADIUS_EQUATION, y));

        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.C7, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.E7, x + KNIGHT_RADIUS_EQUATION, y));

        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.F4, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.F6, x + KNIGHT_RADIUS_EQUATION, y));

        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.C3, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertTrue(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.E3, x + KNIGHT_RADIUS_EQUATION, y));

        Assert.assertFalse(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.D7, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertFalse(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.D3, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertFalse(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.B5, x + KNIGHT_RADIUS_EQUATION, y));
        Assert.assertFalse(MathUtils.isPositionOnCirclePerimeter(D_5, CasePosition.F5, x + KNIGHT_RADIUS_EQUATION, y));
    }

    @Test
    public void getDistanceBetweenPositions() {
        Assert.assertNull(MathUtils.getDistanceBetweenPositionsWithCommonDirection(D_5, D_5));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H1, CasePosition.H8));
        Assert.assertEquals(Integer.valueOf(4), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H1, CasePosition.H5));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H8, CasePosition.H1));
        Assert.assertEquals(Integer.valueOf(4), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H5, CasePosition.H1));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H5, CasePosition.A5));
        Assert.assertEquals(Integer.valueOf(3), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H5, CasePosition.E5));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.A5, CasePosition.H5));
        Assert.assertEquals(Integer.valueOf(3), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.E5, CasePosition.H5));
        Assert.assertEquals(Integer.valueOf(9), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.H1, CasePosition.A8));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.F3, CasePosition.A8));
        Assert.assertEquals(Integer.valueOf(9), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.A8, CasePosition.H1));
        Assert.assertEquals(Integer.valueOf(7), MathUtils.getDistanceBetweenPositionsWithCommonDirection(CasePosition.A8, CasePosition.F3));
    }

    @Test
    public void getNearestPositionFromDirection() {
        Assert.assertEquals(CasePosition.D6, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH));
        Assert.assertEquals(CasePosition.D4, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH));
        Assert.assertEquals(CasePosition.C5, MathUtils.getNearestPositionFromDirection(D_5, Direction.WEST));
        Assert.assertEquals(CasePosition.E5, MathUtils.getNearestPositionFromDirection(D_5, Direction.EAST));

        Assert.assertEquals(CasePosition.C6, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH_WEST));
        Assert.assertEquals(CasePosition.E6, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH_EAST));

        Assert.assertEquals(CasePosition.C4, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH_WEST));
        Assert.assertEquals(CasePosition.E4, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH_EAST));


        Assert.assertEquals(CasePosition.D7, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH, 2));
        Assert.assertEquals(CasePosition.D3, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH, 2));
        Assert.assertEquals(CasePosition.B5, MathUtils.getNearestPositionFromDirection(D_5, Direction.WEST, 2));
        Assert.assertEquals(CasePosition.F5, MathUtils.getNearestPositionFromDirection(D_5, Direction.EAST, 2));

        Assert.assertEquals(CasePosition.B7, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH_WEST, 2));
        Assert.assertEquals(CasePosition.F7, MathUtils.getNearestPositionFromDirection(D_5, Direction.NORTH_EAST, 2));

        Assert.assertEquals(CasePosition.B3, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH_WEST, 2));
        Assert.assertEquals(CasePosition.F3, MathUtils.getNearestPositionFromDirection(D_5, Direction.SOUTH_EAST, 2));
    }

    @Test
    public void getDirectionFromPosition() {

        Assert.assertNull(MathUtils.getDirectionFromPosition(D_5, D_5));

        Assert.assertEquals(NORTH, MathUtils.getDirectionFromPosition(D_5, CasePosition.D6));
        Assert.assertEquals(NORTH, MathUtils.getDirectionFromPosition(D_5, CasePosition.D8));

        Assert.assertEquals(NORTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.C8));
        Assert.assertEquals(NORTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.C6));
        Assert.assertEquals(NORTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.A8));

        Assert.assertEquals(WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.A5));
        Assert.assertEquals(WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.C5));

        Assert.assertEquals(SOUTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.B4));
        Assert.assertEquals(SOUTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.C4));
        Assert.assertEquals(SOUTH_WEST, MathUtils.getDirectionFromPosition(D_5, CasePosition.A1));

        Assert.assertEquals(SOUTH, MathUtils.getDirectionFromPosition(D_5, CasePosition.D4));
        Assert.assertEquals(SOUTH, MathUtils.getDirectionFromPosition(D_5, CasePosition.D1));

        Assert.assertEquals(SOUTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.F1));
        Assert.assertEquals(SOUTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.E4));
        Assert.assertEquals(SOUTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.H1));

        Assert.assertEquals(EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.E5));
        Assert.assertEquals(EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.H5));

        Assert.assertEquals(NORTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.G6));
        Assert.assertEquals(NORTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.E6));
        Assert.assertEquals(NORTH_EAST, MathUtils.getDirectionFromPosition(D_5, CasePosition.H8));
    }

    @Test
    public void getSlopeFromPosition() {
        Assert.assertNull(MathUtils.getSlopeFromPosition(D_5, D_5));
        Assert.assertEquals(-1f, MathUtils.getSlopeFromPosition(CasePosition.B8, CasePosition.H2), DELTA_SLOPE_TEST);
        Assert.assertEquals(7 / 3f, MathUtils.getSlopeFromPosition(CasePosition.E1, CasePosition.H8), DELTA_SLOPE_TEST);
    }

    @Test
    public void isPositionInLine() {
        Assert.assertTrue(MathUtils.isPositionInLine(CasePosition.D6, CasePosition.E5, CasePosition.H2));
        Assert.assertTrue(MathUtils.isPositionInLine(CasePosition.A6, CasePosition.B5, CasePosition.D3));
        Assert.assertTrue(MathUtils.isPositionInLine(CasePosition.H8, CasePosition.G7, CasePosition.C3));
        Assert.assertTrue(MathUtils.isPositionInLine(CasePosition.E4, CasePosition.D4, CasePosition.A4));
        Assert.assertTrue(MathUtils.isPositionInLine(CasePosition.E4, CasePosition.E5, CasePosition.E8));
        Assert.assertFalse(MathUtils.isPositionInLine(CasePosition.H8, CasePosition.G7, CasePosition.C4));
    }
}