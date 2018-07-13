package com.codepath.android.lollipopexercise.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.activities.DetailsActivity;
import com.codepath.android.lollipopexercise.models.Contact;

import java.util.List;

// Provide the underlying view for an individual list item.
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.VH> {
    private Activity mContext;
    private List<Contact> mContacts;

    public ContactsAdapter(Activity context, List<Contact> contacts) {
        mContext = context;
        if (contacts == null) {
            throw new IllegalArgumentException("contacts must not be null");
        }
        mContacts = contacts;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        Contact contact = mContacts.get(position);
        holder.rootView.setTag(contact);
        holder.tvName.setText(contact.getName());


        // Use Glide to get a callback with a Bitmap which can then
        // be used to extract a vibrant color from the Palette.

        // Define a listener for image loading
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                // TODO 1. Insert the bitmap into the profile image view

                // TODO 2. Use generate() method from the Palette API to get the vibrant color from the bitmap
                // Set the result as the background color for `R.id.vPalette` view containing the contact's name.
                Palette palette = Palette.from(resource).generate();
                // Pick one of the swatches
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null) {
                    // Set the background color of a layout based on the vibrant color
                 //  holder.ivProfile.setBackgroundColor(vibrant.getRgb());
                    // Update the title TextView with the proper text color
                  //  holder.tvName.setTextColor(vibrant.getTitleTextColor());
                  //  holder.tvName.setTextColor(vibrant.getTitleTextColor());
                }
            }
        };

        // TODO: Clear the bitmap and the background color in adapter
        // Store the target into the tag for the profile to ensure target isn't garbage collected prematurely
        holder.ivProfile.setTag(target);
        // Instruct Picasso to load the bitmap into the target defined above
        Glide.with(mContext).load(contact.getThumbnailDrawable()).asBitmap().centerCrop().into(target);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    // Provide a reference to the views for each contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
        final View vPalette;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Contact contact = (Contact) v.getTag();
                    if (contact != null) {
                        // Fire an intent when a contact is selected
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        // Pass contact object in the bundle and populate details activity.
                        intent.putExtra("EXTRA_CONTACT", contact);
                        mContext.startActivity(intent);
                    }
                }
            });
        }


    }
}
