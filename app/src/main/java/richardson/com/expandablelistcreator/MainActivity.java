package richardson.com.expandablelistcreator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private List<Map<String, String>> parentList;
    private List<List<Map<String, String>>> childLists;
    private String[] parentNames;
    private String[][] childrenNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expListView = (ExpandableListView)findViewById(R.id.expandableListView);

        /*if used in a fragment rather than activity
            in onCreateView method
            View view = inflater.inflate(**resource ID for layout**)
            inflater received in method call

            retrieve parent and child string arrays
            child arrays placed into array of arrays for proper grouping
         */

        parentNames = getResources().getStringArray(R.array.parent_group);
        childrenNames = new String[][]{
                getResources().getStringArray(R.array.group_A_children),
                getResources().getStringArray(R.array.group_B_children),
                getResources().getStringArray(R.array.group_C_children),
                getResources().getStringArray(R.array.group_D_children),
                getResources().getStringArray(R.array.group_E_children)
        };

        makeParentList();
        makeChildLists();

        /*
            if used in a fragment, the "this" parameter should be  proper context,
            usually getActivity()
         */

        expListView.setAdapter(new SimpleExpandableListAdapter(
                this, parentList, android.R.layout.simple_expandable_list_item_1,
                new String[]{"PARENT"}, new int[] {android.R.id.text1},

                childLists, android.R.layout.simple_expandable_list_item_2,
                new String[]{"CHILD", "CHILD"}, new int[] {android.R.id.text1, android.R.id.text2 }
        ));
    }

    private void makeParentList(){
        /*  parentList is a list of maps as required by the simpleExpandableListAdapter
            constructor: List<Map<String, String>>.

            Each group parent requires a map. One map is created for each string in parentNames
            array and then added into the parentList list.

         */

        parentList = new ArrayList<>();
        for (int arrayIndex = 0; arrayIndex < parentNames.length; ++ arrayIndex){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("PARENT", parentNames[arrayIndex]);
            parentList.add(hm);
        }
    }

    private void makeChildLists(){
        /*
            childLists is a list of lists of maps as required by the simpleExpandableListAdapter
            constructor: List<List<Map<String, String>>>.

            An inner list is created for each string in the group parent array. Then, maps are
            created for each child item in each group.
         */
        childLists = new ArrayList<>();

        for (int parentElement = 0; parentElement < parentNames.length; ++parentElement){
            List<Map<String, String>> child_inner_list = new ArrayList<>();
            for (int childElement = 0; childElement < childrenNames[parentElement].length;
                 ++childElement){
                Map<String, String> map = new HashMap<>();
                map.put("CHILD", childrenNames[parentElement][childElement]);
                child_inner_list.add(map);
            }
            childLists.add(child_inner_list);
        }
    }
}




