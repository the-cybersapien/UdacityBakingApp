package xyz.cybersapien.miriamslittlebakery.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Recipe(
        @SerializedName("id") var id: Int,
        @SerializedName("name") var name: String,
        @SerializedName("servings") var servings: Int,
        @SerializedName("image") var image: String,
        @SerializedName("ingredients") var ingredients: ArrayList<Ingredient> = ArrayList(),
        @SerializedName("steps") var steps: ArrayList<Step> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            mutableListOf<Ingredient>().apply {
                parcel.readTypedList(this, Ingredient.CREATOR)
            } as ArrayList<Ingredient>,
            mutableListOf<Step>().apply {
                parcel.readTypedList(this, Step.CREATOR)
            } as ArrayList<Step>
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(servings)
        parcel.writeString(image)
        parcel.writeTypedList(ingredients)
        parcel.writeTypedList(steps)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}