package com.example.quotesapp.Adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quotesapp.Model.Quotes;
import com.example.quotesapp.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Quotes> arrayList;
    int[] colors;

    public CustomAdapter(Context context, ArrayList<Quotes> arrayList, int[] colors) {
        this.context = context;
        this.arrayList = arrayList;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams", "ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.list_item, null);

        TextView quoteTextView = view.findViewById(R.id.quoteText);
        TextView authorTextView = view.findViewById(R.id.authorText);
        ImageView shareBtn = view.findViewById(R.id.shareBtn);
        ImageView copyBtn = view.findViewById(R.id.copyBtn);

        Quotes quote = arrayList.get(i);

        quoteTextView.setText(quote.getQuote());
        authorTextView.setText("- " + quote.getAuthor());

        view.setBackgroundColor(getRandom());

        shareBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, quoteTextView.getText().toString() + "\n\n" + authorTextView.getText().toString());
            intent.setType("text/plain");
            context.startActivity(intent);
        });

        copyBtn.setOnClickListener(view2 -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("quote", quoteTextView.getText().toString() + "\n\n" + authorTextView.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    public int getRandom() {
        int random = (int) Math.floor(Math.random() * colors.length);
        return colors[random];
    }
}
