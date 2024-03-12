package com.example.electronicgadgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var productAdapter: ProductAdapter
    private val cart = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val products = arrayListOf<Product>(
            Product("iPad", "iPad Pro 11-inch", 400.0),
            Product("MacBook M3 Pro", "12-core CPU\n18-core GPU", 2500.0),
            Product("Dell Inspiron", "13th Gen Intel® Core™ i7", 1499.0),
            Product("Logitech Keyboard", "Logitech - PRO X\nTKL LIGHTSPEED Wireless", 199.0),
            Product("MacBook M3 Max", "14-core CPU\n30-core GPU", 3499.0)
        )

        productAdapter = ProductAdapter(products, ::addToCart)
        findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }

        findViewById<Button>(R.id.viewCart).setOnClickListener {
            val cartItems = cart.joinToString("\n") { it.productName }
            Toast.makeText(this, "Cart Items:\n$cartItems", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToCart(product: Product) {
        cart.add(product)
    }
}

// Product.kt
data class Product(val productName: String, val productDescription: String, val cost: Double)

// ProductAdapter.kt
class ProductAdapter(
    private val products: List<Product>,
    private val onAddToCart: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.apply {
                findViewById<TextView>(R.id.productName).text = product.productName
                findViewById<TextView>(R.id.productDescription).text = product.productDescription
                findViewById<TextView>(R.id.productCost).text = "$ ${product.cost}"
                findViewById<Button>(R.id.addToCart).setOnClickListener {
                    onAddToCart(product)
                }
            }
        }
    }
}