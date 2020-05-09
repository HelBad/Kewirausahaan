package com.example.kewirausahaan

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pemesanan.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ActivityPemesanan : AppCompatActivity() {

    lateinit var tipeArmada: TextView
    lateinit var jenisArmada:TextView
    lateinit var tahunArmada:TextView
    lateinit var jumlahArmada:TextView
    lateinit var deskripsiArmada:TextView
    lateinit var textImgArmada:TextView
    lateinit var gambarArmada: ImageView
    lateinit var databaseReference: DatabaseReference
    lateinit var alertDialog: AlertDialog.Builder

    lateinit var spinnerBerangkat: Spinner
    lateinit var textBerangkat: TextView
    lateinit var spinnerTujuan: Spinner
    lateinit var textTujuan: TextView

    val formatHari = SimpleDateFormat("EEEE")
    var formatTgl = SimpleDateFormat("dd MMM YYYY")
    val berangkat = Calendar.getInstance()
    val pulang = Calendar.getInstance()
    var formatWaktu = SimpleDateFormat("hh:mm aa")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)

        tipeArmada = findViewById(R.id.tipeAPemesanan)
        jenisArmada = findViewById(R.id.jenisAPemesanan)
        tahunArmada = findViewById(R.id.tahunAPemesanan)
        jumlahArmada = findViewById(R.id.jumlahAPemesanan)
        deskripsiArmada = findViewById(R.id.deskripsiAPemesanan)
        textImgArmada = findViewById(R.id.textImgAPemesanan)
        gambarArmada = findViewById(R.id.gambarAPemesanan)
        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("Armada").child(intent.getStringExtra("jenis")).orderByChild("tipe").equalTo(intent.getStringExtra("tipe"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null)
                {
                    for (snapshot1 in datasnapshot.children)
                    {
                        val allocation = snapshot1.getValue(ListArmada::class.java)
                        tipeArmada.text = allocation!!.tipe
                        jenisArmada.text = allocation.jenis
                        tahunArmada.text = allocation.tahun
                        jumlahArmada.text = allocation.jumlah
                        deskripsiArmada.text = allocation.deskripsi
                        textImgArmada.text = allocation.gambar1
                        Picasso.get().load(allocation.gambar1).into(gambarArmada)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        spinnerBerangkat = findViewById(R.id.spinnerBerangkat)
        textBerangkat = findViewById(R.id.textBerangkat)
        val lokasiBerangkat = arrayOf("Malang", "Surabaya")
        spinnerBerangkat.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lokasiBerangkat)
        spinnerBerangkat.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textBerangkat.text = "Pilih Lokasi"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textBerangkat.text = lokasiBerangkat[position]
            }
        }

        spinnerTujuan = findViewById(R.id.spinnerTujuan)
        textTujuan = findViewById(R.id.textTujuan)
        val lokasiTujuan = arrayOf("DKI Jakarta", "D.I. Yogyakarta", "Pulau Bali")
        spinnerTujuan.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lokasiTujuan)
        spinnerTujuan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textTujuan.text = "Pilih Lokasi"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textTujuan.text = lokasiTujuan[position]
            }
        }

        imgBerangkat.setOnClickListener {
            val dateBerangkat = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = Date(year, month, dayOfMonth - 1)
                val dayString = formatHari.format(date)

                klikBerangkat.text = ""
                tanggalBerangkat.text = "$dayString, " + formatTgl.format(selectedDate.time)
                val jumlahHari = dayOfMonth.toString().toInt() + month.toString().toInt() * 30 + year.toString().toInt() * 360
                jumlahHariBerangkat.text = jumlahHari.toString()
            }, berangkat.get(Calendar.YEAR), berangkat.get(Calendar.MONTH), berangkat.get(Calendar.DAY_OF_MONTH))
            dateBerangkat.show()
        }

        imgPulang.setOnClickListener {
            val datePulang = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = Date(year, month, dayOfMonth - 1)
                val dayString = formatHari.format(date)

                klikPulang.text = ""
                tanggalPulang.text = "$dayString, " + formatTgl.format(selectedDate.time)
                val jumlahHari = dayOfMonth.toString().toInt() + month.toString().toInt() * 30 + year.toString().toInt() * 360
                jumlahHariPulang.text = jumlahHari.toString()
            }, pulang.get(Calendar.YEAR), pulang.get(Calendar.MONTH), pulang.get(Calendar.DAY_OF_MONTH))
            datePulang.show()
        }

        imgJamBerangkat.setOnClickListener {
            val timeBerangkat = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                view, hourOfDay, minute -> val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                klikJamBerangkat.text = ""
                jamBerangkat.text = formatWaktu.format(selectedTime.time)
            }, berangkat.get(Calendar.HOUR_OF_DAY), berangkat.get(Calendar.MINUTE), false)
            timeBerangkat.show()
        }

        imgJamPulang.setOnClickListener {
            val timePulang = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                view, hourOfDay, minute -> val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                klikJamPulang.text = ""
                jamPulang.text = formatWaktu.format(selectedTime.time)
            }, pulang.get(Calendar.HOUR_OF_DAY), pulang.get(Calendar.MINUTE), false)
            timePulang.show()
        }

        cekBiaya.setOnClickListener {
            if (jumlahUnit.text.toString().toInt() <= jumlahAPemesanan.text.toString().toInt() && jumlahUnit.text.toString().toInt() >= 1 && jumlahUnit.text.isNotEmpty()) {
                if(tanggalBerangkat.text.isNotEmpty() && tanggalPulang.text.isNotEmpty() && jumlahHariBerangkat.text.toString().toInt() <= jumlahHariPulang.text.toString().toInt()) {
                    if(jenisArmada.text.toString() == "Minibus Big") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 18900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 16400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 19000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 18900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 16400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 19000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Minibus Jumbo") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 10900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Minibus Big Jumbo") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 15900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 14000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 15900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 14000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Minibus MC") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 13100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3700000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 11200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 13100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3700000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 11200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Mobil") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 8100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 2300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 4500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 6200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 5900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 8100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 2300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 4500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 3900000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 6200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Big Bus SHD") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 16200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 33000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12600000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 29400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 14200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 31000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 16200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 33000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12600000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 29400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 14200000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 31000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Big Bus MD") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 11100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 22300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 18700000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 20400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (textBerangkat.text.toString() == "Malang") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 11100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 22300000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 7500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 18700000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 9100000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 20400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if(jenisArmada.text.toString() == "Big Bus HDD") {
                        if (textBerangkat.text.toString() == "Surabaya") {
                            if (textTujuan.text.toString() == "DKI Jakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 15600000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 32400000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 12000000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 28800000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            } else if (textTujuan.text.toString() == "Pulau Bali") {
                                if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                    textPaket.text = "Paket Reguler"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 13600000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                    textPaket.text = "Paket Komplet"
                                    val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                    val jumlahArmada = jumlahUnit.text.toString().toInt()
                                    val totalBiaya = 30500000 * jumlahHari * jumlahArmada

                                    var formatter: NumberFormat = DecimalFormat("#,###")
                                    textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                                } else {
                                    Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else if (textBerangkat.text.toString() == "Malang") {
                        if (textTujuan.text.toString() == "DKI Jakarta") {
                            if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                textPaket.text = "Paket Reguler"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 15600000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                textPaket.text = "Paket Komplet"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 32400000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else {
                                Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                            }
                        } else if (textTujuan.text.toString() == "D.I. Yogyakarta") {
                            if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                textPaket.text = "Paket Reguler"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 12000000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                textPaket.text = "Paket Komplet"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 28800000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else {
                                Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                            }
                        } else if (textTujuan.text.toString() == "Pulau Bali") {
                            if (paketFasilitas.checkedRadioButtonId == R.id.paket1) {
                                textPaket.text = "Paket Reguler"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 13600000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else if (paketFasilitas.checkedRadioButtonId == R.id.paket2) {
                                textPaket.text = "Paket Komplet"
                                val jumlahHari = jumlahHariPulang.text.toString().toInt() - jumlahHariBerangkat.text.toString().toInt() + 1
                                val jumlahArmada = jumlahUnit.text.toString().toInt()
                                val totalBiaya = 30500000 * jumlahHari * jumlahArmada

                                var formatter: NumberFormat = DecimalFormat("#,###")
                                textBiaya.text = "Rp. " + formatter.format(totalBiaya) + ",00"
                            } else {
                                Toast.makeText(this, "Pilih paket fasilitas yang tersedia", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Tanggal tidak sesuai", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Jumlah unit tidak sesuai", Toast.LENGTH_SHORT).show()
            }
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Pesanan")
        btnkonfirm.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                addData()
            }
        })
    }

    private fun addData() {
        val gambararmada = textImgAPemesanan.text.toString().trim()
        val tipearmada = tipeArmada.text.toString().trim()
        val jenisarmada = jenisArmada.text.toString().trim()
        val tahunarmada = tahunArmada.text.toString().trim()
        val deskripsiarmada = deskripsiArmada.text.toString().trim()
        val namacostumer = namaPemesan.text.toString().trim()
        val emailcostumer = emailPemesan.text.toString().trim()
        val lokasiberangkat = textBerangkat.text.toString().trim()
        val lokasitujuan = textTujuan.text.toString().trim()
        val tanggalberangkat = tanggalBerangkat.text.toString().trim()
        val jamberangkat = jamBerangkat.text.toString().trim()
        val tanggalpulang = tanggalPulang.text.toString().trim()
        val jampulang = jamPulang.text.toString().trim()
        val jumlahunit = jumlahUnit.text.toString().trim()
        val fasilitas = textPaket.text.toString().trim()
        val biaya = textBiaya.text.toString().trim()

        if (!TextUtils.isEmpty(namacostumer) && !TextUtils.isEmpty(namacostumer) && !TextUtils.isEmpty(jamberangkat)
            && !TextUtils.isEmpty(jampulang) && !TextUtils.isEmpty(biaya))
        {
            val pesan = Pesanan(gambararmada, tipearmada, jenisarmada, tahunarmada, deskripsiarmada, namacostumer, emailcostumer,
                lokasiberangkat, lokasitujuan, tanggalberangkat, jamberangkat, tanggalpulang, jampulang, jumlahunit, fasilitas, biaya)
            databaseReference.child(tanggalberangkat + " (" + namacostumer + ")").setValue(pesan)
            Toast.makeText(this, "Lanjutkan Transaksi di Halaman Utama bagian Aktivitas", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
        }
    }
}