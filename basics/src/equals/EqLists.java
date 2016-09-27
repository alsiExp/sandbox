package equals;

import model.NamedEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EqLists {
    public static NamedEntity firstEntity = new NamedEntity(0, "firstEntity");
    public static NamedEntity secondEntity = new NamedEntity(1, "secondEntity");
    public static NamedEntity thirdEntity = new NamedEntity(2, "thirdEntity");

    public static List<NamedEntity> firstList = Arrays.asList(firstEntity,secondEntity, thirdEntity);
    public static List<NamedEntity> secondList = Arrays.asList(firstEntity, thirdEntity, secondEntity);

    public static void main(String[] args) {
        System.out.println(firstList.equals(secondList));

        Collections.sort(firstList,new ListEntityComparator());
        Collections.sort(secondList,new ListEntityComparator());

        System.out.println(firstList.equals(secondList));
    }

    public static class ListEntityComparator implements Comparator<NamedEntity> {
        @Override
        public int compare(NamedEntity o1, NamedEntity o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }




}
