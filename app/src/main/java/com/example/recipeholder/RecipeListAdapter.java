package com.example.recipeholder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeholder.RecipeDB.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private Context context;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout containerView;
        Button recipeText, recipeTime;

        Button verifyDelete;

        RecipeViewHolder(View view){
            super(view);

            containerView = view.findViewById(R.id.recipe_row);
            recipeText = view.findViewById(R.id.recipe_name);
            recipeTime = view.findViewById(R.id.recipe_time_to_make);




            //option to delete recipe from database
            containerView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog dialogBuilder = new AlertDialog.Builder(containerView.getContext()).create();
                    LayoutInflater inflater = (LayoutInflater) containerView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View dialogView = inflater.inflate(R.layout.recipe_delete, null);


                    dialogBuilder.setView(dialogView);
                    dialogBuilder.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                    dialogBuilder.getWindow().setDimAmount(0.0f);

                    verifyDelete = dialogView.findViewById(R.id.recipe_remove);
                    //deleting recipe from database
                    verifyDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Recipe recipe = (Recipe) containerView.getTag();
                            MainActivity.database.recipeDAO().delete(recipe.id);
                            MainActivity.adapter.reload(MainActivity.Taste, MainActivity.Type);
                            dialogBuilder.dismiss();
                        }
                    });

                    dialogBuilder.show();
                    return true;
                }
            });

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recipe recipe = (Recipe) containerView.getTag();
                    //Log.e("browse", "goes");
                    /*
                     Intent i = new Intent("android.intent.action.MAIN");
                     i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                     i.addCategory("android.intent.category.LAUNCHER");
                     i.setData(Uri.parse(recipe.url));
                     v.getContext().startActivity(i);
                       */

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url));
                    v.getContext().startActivity(browserIntent);
                }
            });

        }
    }

    private List<Recipe> recipes= new ArrayList<>();

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO recipe_row layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe current = recipes.get(position);

        holder.recipeText.setText(current.name);
        holder.recipeTime.setText(Values.timeString(current.time));

        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void reload(int taste, int type){
        recipes = MainActivity.database.recipeDAO().getAll();
        recipes = Filter.filter(recipes, taste, type);

        notifyDataSetChanged();
        notifyItemRangeChanged(0, recipes.size());
    }

}
