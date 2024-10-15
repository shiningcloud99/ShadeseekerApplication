package com.example.myapplication.ui.create;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Brand;
import com.example.myapplication.Product;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {
    private static final String TAG = "CreateFragment";

    private EditText brandName, brandDescription, productName, productDescription, productShade, productTone, productCode;
    private Button uploadBrandButton, selectImageButton, uploadProductButton, selectProductImageButton;
    private ImageView brandImageView, productImageView;
    private ProgressBar progressBar;
    private DatabaseReference brandsRef, productsRef;
    private StorageReference storageRef;
    private Uri imageUri, productImageUri;
    private Spinner brandSpinner;
    private List<String> brandList = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher, productImageActivityResultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_create, container, false);

        // Initialize UI components
        initializeUIComponents(root);

        // Initialize Firebase references
        brandsRef = FirebaseDatabase.getInstance().getReference("brands");
        productsRef = FirebaseDatabase.getInstance().getReference("products");
        storageRef = FirebaseStorage.getInstance().getReference("images");

        selectImageButton.setOnClickListener(v -> openFileChooser("brand"));

        uploadBrandButton.setOnClickListener(v -> {
            if (imageUri != null) {
                uploadImage("brand");
            } else {
                uploadBrand(null);
            }
        });

        selectProductImageButton.setOnClickListener(v -> openFileChooser("product"));

        uploadProductButton.setOnClickListener(v -> {
            if (productImageUri != null) {
                uploadImage("product");
            } else {
                uploadProduct(null);
            }
        });

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        if (getContext() != null) {
                            Picasso.get().load(imageUri).into(brandImageView);
                        }
                    }
                });

        productImageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        productImageUri = result.getData().getData();
                        if (getContext() != null) {
                            Picasso.get().load(productImageUri).into(productImageView);
                        }
                    }
                });

        // Add TextWatchers to productTone and productShade
        productTone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generateProductCode();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        productShade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generateProductCode();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        populateBrandSpinner();

        return root;
    }

    private void initializeUIComponents(View root) {
        brandName = root.findViewById(R.id.brandName);
        brandDescription = root.findViewById(R.id.brandDescription);
        uploadBrandButton = root.findViewById(R.id.uploadBrandButton);
        selectImageButton = root.findViewById(R.id.selectImageButton);
        brandImageView = root.findViewById(R.id.brandImageView);

        productName = root.findViewById(R.id.productName);
        productDescription = root.findViewById(R.id.productDescription);
        productShade = root.findViewById(R.id.productShade);
        productTone = root.findViewById(R.id.productTone);
        productCode = root.findViewById(R.id.productCode);
        selectProductImageButton = root.findViewById(R.id.selectProductImageButton);
        productImageView = root.findViewById(R.id.productImageView);
        uploadProductButton = root.findViewById(R.id.uploadProductButton);
        progressBar = root.findViewById(R.id.progressBar);

        brandSpinner = root.findViewById(R.id.brandSpinner);
    }

    private void populateBrandSpinner() {
        brandsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                brandList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Brand brand = dataSnapshot.getValue(Brand.class);
                    if (brand != null) {
                        brandList.add(brand.getName());
                    }
                }
                if (getContext() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, brandList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    brandSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load brands", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load brands", error.toException());
            }
        });
    }

    private void openFileChooser(String type) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (type.equals("brand")) {
            activityResultLauncher.launch(intent);
        } else {
            productImageActivityResultLauncher.launch(intent);
        }
    }

    private void uploadImage(String type) {
        Uri fileUri = type.equals("brand") ? imageUri : productImageUri;
        if (fileUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(fileUri));
            fileReference.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        if (type.equals("brand")) {
                            uploadBrand(uri.toString());
                        } else {
                            uploadProduct(uri.toString());
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }))
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to upload image", e);
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        if (getActivity() != null) {
            ContentResolver cR = getActivity().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cR.getType(uri));
        }
        return null;
    }

    private void generateProductCode() {
        String tone = productTone.getText().toString().trim();
        String shade = productShade.getText().toString().trim();

        if (!tone.isEmpty() && !shade.isEmpty()) {
            String code = tone.substring(0, 1).toUpperCase() + shade;
            productCode.setText(code);
        } else {
            productCode.setText("");
        }
    }

    private void uploadBrand(String imageUrl) {
        String name = brandName.getText().toString().trim();
        String description = brandDescription.getText().toString().trim();

        if (!name.isEmpty()) {
            String id = brandsRef.push().getKey();
            Brand brand = new Brand(id, name, description, imageUrl);
            if (id != null) {
                brandsRef.child(id).setValue(brand)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getActivity(), "Brand uploaded!", Toast.LENGTH_SHORT).show();
                            brandName.setText("");
                            brandDescription.setText("");
                            brandImageView.setImageResource(android.R.color.transparent);
                            imageUri = null;
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getActivity(), "Failed to upload brand: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to upload brand", e);
                        });
            }
        } else {
            Toast.makeText(getActivity(), "Please fill the brand name", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProduct(String imageUrl) {
        String brand = brandSpinner.getSelectedItem().toString();
        String name = productName.getText().toString().trim();
        String description = productDescription.getText().toString().trim();
        String shade = productShade.getText().toString().trim();
        String tone = productTone.getText().toString().trim();
        String code = productCode.getText().toString().trim();


        if (!brand.isEmpty()) {
            String id = productsRef.push().getKey(); // Use ssProductsRef instead of productsRef
            Product product = new Product(id, name, description, shade, tone, code, imageUrl, brand);
            if (id != null) {
                productsRef.child(id).setValue(product) // Use ssProductsRef instead of productsRef
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getActivity(), "Product uploaded!", Toast.LENGTH_SHORT).show();
                            productName.setText("");
                            productDescription.setText("");
                            productShade.setText("");
                            productTone.setText("");
                            productCode.setText("");
                            productImageView.setImageResource(android.R.color.transparent);
                            productImageUri = null;
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getActivity(), "Failed to upload product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to upload product", e);
                        });
            }
        } else {
            Toast.makeText(getActivity(), "Please fill all the product fields", Toast.LENGTH_SHORT).show();
        }
    }
}
