package com.emrealtunbilek.instakotlinapp.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emrealtunbilek.instakotlinapp.Models.UserPosts
import com.emrealtunbilek.instakotlinapp.R
import kotlinx.android.synthetic.main.tek_sutun_grid_resim_profil.view.*
import org.greenrobot.eventbus.EventBus
import android.os.Build
import android.graphics.Bitmap



/**
 * Created by Emre on 3.06.2018.
 */
class ProfilePostGridRecyclerAdapter(var kullaniciPostlari:ArrayList<UserPosts>, var myContext:Context): RecyclerView.Adapter<ProfilePostGridRecyclerAdapter.MyViewHolder>() {

    lateinit var inflater:LayoutInflater

    init {
        inflater= LayoutInflater.from(myContext)
    }

    override fun getItemCount(): Int {

        return kullaniciPostlari.size

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var tekSutunDosya=inflater.inflate(R.layout.tek_sutun_grid_resim_profil, parent, false)

        return MyViewHolder(tekSutunDosya)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var dosyaYolu= kullaniciPostlari.get(position).postURL
        var noktaninGectigiIndex=dosyaYolu!!.lastIndexOf(".")
        var dosyaTuru=dosyaYolu!!.substring(noktaninGectigiIndex, noktaninGectigiIndex+4)


        if(dosyaTuru.equals(".mp4")){
            Log.e("DOSYA TURU","DOSYA TURU"+ dosyaTuru)
            holder.videoIcon.visibility=View.VISIBLE
            var thumnnailFoto=videodanThumbnailOlustur(dosyaYolu)
            holder.dosyaResim.setImageBitmap(thumnnailFoto)
            holder.dosyaProgressBar.visibility=View.GONE
        }else {
            Log.e("DOSYA TURU","DOSYA TURU"+ dosyaTuru)
            holder.videoIcon.visibility=View.GONE
            holder.dosyaProgressBar.visibility=View.VISIBLE
            UniversalImageLoader.setImage(dosyaYolu!!, holder.dosyaResim, holder.dosyaProgressBar,"")
        }

    }

    @Throws(Throwable::class)
    fun videodanThumbnailOlustur(videoPath: String): Bitmap? {
        var bitmap: Bitmap? = null
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, HashMap())
            else
                mediaMetadataRetriever.setDataSource(videoPath)

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release()
            }
        }
        return bitmap
    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var tekSutunDosya=itemView as ConstraintLayout
        var videoIcon=tekSutunDosya.imgVideoIcon
        var dosyaResim=tekSutunDosya.imgTekSutunImage
        var dosyaProgressBar=tekSutunDosya.progressBar



    }



}