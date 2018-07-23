package com.storyview.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.storyview.Model.Contact;
import com.storyview.Lisener.onClickStatus;
import com.storyview.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.MyViewHolder> {

    private List<Contact> moviesList;
    Context mcontext;
    onClickStatus onClickStatus;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price,catid;
        public LinearLayout ll;
        public CircleImageView img;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt1);
            ll= view.findViewById(R.id.ll);
            img= view.findViewById(R.id.img);
        }
    }


    public DataBaseAdapter(Context mcontext, List<Contact> moviesList,onClickStatus onClickStatus) {
        this.mcontext = mcontext;
        this.moviesList = moviesList;
        this.onClickStatus= onClickStatus;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowdatabase, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Contact movie = moviesList.get(position);
        holder.title.setText(""+movie.getDate());

        File imgFile = new File(movie.getImagepath());
        if(imgFile.exists())
        {
            holder.img.setImageURI(Uri.fromFile(imgFile));

        }

//        String filePath  =movie.getStatus();
//        File sd = Environment.getExternalStorageDirectory();
//        File image = new File(sd+filePath, imageName);
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
//        holder.img.setImageBitmap(bitmap);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onClickStatus.onClickStatusRow(position);

            }
        });
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}