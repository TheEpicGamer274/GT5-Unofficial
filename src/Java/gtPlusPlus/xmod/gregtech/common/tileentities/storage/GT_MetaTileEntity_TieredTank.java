package gtPlusPlus.xmod.gregtech.common.tileentities.storage;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GregtechMetaBasicTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_TieredTank
        extends GregtechMetaBasicTank {
	
	protected FluidStack internalTank = getInternalStack();
	protected String fluidName = getFluidName();
	protected int fluidAmount = getInternalFluidAmount();
	
	private String getFluidName(){
		String x;
		if (internalTank != null){
			x = internalTank.getFluid().getName();
		}
		else {
			x = "null";
		}
		return x;
	}
	
	private int getInternalFluidAmount(){
		int x;
		if (internalTank != null){
			x = internalTank.amount;
		}
		else {
			x = 0;
		}
		return x;
	}
	
    public GT_MetaTileEntity_TieredTank(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 3, "Stores " + ((int) (Math.pow(2, aTier) * 32000)) + "L of fluid");
    }

    public GT_MetaTileEntity_TieredTank(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return new ITexture[0][0][0];
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return aSide == 1 ? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER_ACTIVE)} : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER)};
    }

   /* @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }
    
    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }*/
    
    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        Utils.LOG_INFO("Dumping Fluid data. Name: "+mFluid.getFluid().getName()+" Amount: "+mFluid.amount+"L");
        if (mFluid != null) aNBT.setTag("mFluid", mFluid.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mFluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mFluid"));
        if (getInternalStack() != null){
        Utils.LOG_INFO("Dumping Fluid data. Name: "+mFluid.getFluid().getName()+" Amount: "+mFluid.amount+"L");}
        else{
        //Utils.LOG_INFO("Loaded FluidStack was NULL");
        	}
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public final byte getUpdateData() {
        return 0x00;
    }

    @Override
    public boolean doesFillContainers() {
        return true;
    }

    @Override
    public boolean doesEmptyContainers() {
        return true;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public boolean displaysItemStack() {
        return true;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    @Override
    public String[] getInfoData() {

        if (mFluid == null) {
            return new String[]{
                    GT_Values.VOLTAGE_NAMES[mTier]+" Fluid Tank",
                    "Stored Fluid:",
                    "No Fluid",
                    Integer.toString(0) + "L",
                    Integer.toString(getCapacity()) + "L"};
        }
        return new String[]{
                GT_Values.VOLTAGE_NAMES[mTier]+" Fluid Tank",
                "Stored Fluid:",
                mFluid.getLocalizedName(),
                Integer.toString(mFluid.amount) + "L",
                Integer.toString(getCapacity()) + "L"};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_TieredTank(mName, mTier, mDescription, mTextures);
    }

    @Override
    public int getCapacity() {
        return (int) (Math.pow(2, mTier) * 32000);
    }

    @Override
    public int getTankPressure() {
        return 100;
    }

}