package ru.dragonestia.dguard.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.*;
import cn.nukkit.blockentity.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPainting;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockBurnEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.DoorToggleEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.inventory.*;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import ru.dragonestia.dguard.DGuard;
import ru.dragonestia.dguard.custom.CustomMethods;
import ru.dragonestia.dguard.region.Region;
import ru.dragonestia.dguard.region.Role;
import ru.dragonestia.dguard.util.Point;

import java.util.Arrays;
import java.util.HashSet;

public class BlockListener implements Listener {

    private final DGuard main;

    private final CustomMethods customMethods;

    private final HashSet<Integer> blockedItems, doors, chests, furnaces, redstone, other;

    public BlockListener(DGuard main){
        this.main = main;
        customMethods = main.getCustomMethods();

        blockedItems = new HashSet<>(Arrays.asList(259, 325, 269, 273, 256, 284, 277, 290, 291, 292, 294, 293));
        doors = new HashSet<>(Arrays.asList(64, 193, 194, 195, 196, 197, 404, 401, 403, 402, 400, 96, 107, 183, 184, 185, 187, 186));
        chests = new HashSet<>(Arrays.asList(458, 205, 54, 146));
        furnaces = new HashSet<>(Arrays.asList(453, 451, 61));
        redstone = new HashSet<>(Arrays.asList(77, 143, 399, 396, 398, 395, 397, 69));
        other = new HashSet<>(Arrays.asList(138, 449, 117, 125, 23, 468, 154, 84, 83, 149));
    }

    private boolean checkBuild(Event event, Player player, Vector3 pos){
        if(event.isCancelled()) return true;

        Region region = new Point(pos).getCacheRegion(player);

        if (region == null) {
            if (main.getSettings().isCanBuildOutRegion()) return false;

            player.sendTip("§cЧтобы строить здесь вам нужно создать регион");
            return true;
        }

        if (customMethods.canDoAllCondition.check(player)) return false;

        switch (region.getRole(player.getName())) {
            case Nobody:
            case Guest:
                player.sendTip("§cУ вас нет доступа чтобы делать это в данном регионе.");
                return true;

        }
        return false;
    }

    private boolean checkTap(PlayerInteractEvent event, Region region, HashSet<Integer> securedId, String flagName){
        if(event.isCancelled()) return true;
        Player player = event.getPlayer();

        if(securedId.contains(event.getBlock().getId())){
            if(region.getRole(player.getName()).equals(Role.Nobody) && !region.getFlag(main.getFlags().get(flagName)) && !customMethods.canDoAllCondition.check(player)){
                player.sendTip("§cУ вас нет доступа к данному региону");
                return true;
            } else return false;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(checkBuild(event, event.getPlayer(), event.getBlock()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(checkBuild(event, event.getPlayer(), event.getBlock()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBucketEmpty(PlayerBucketEmptyEvent event){
        event.setCancelled(checkBuild(event, event.getPlayer(), event.getBlockClicked()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBucketFill(PlayerBucketFillEvent event){
        event.setCancelled(checkBuild(event, event.getPlayer(), event.getBlockClicked()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Point point = new Point(event.getBed());
        Region region = point.getCacheRegion(player);

        if (region == null) return;

        if (region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
            event.setCancelled(true);
            player.sendTip("§cУ вас нет доступа к данному региону");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBurn(BlockBurnEvent event){
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTap(PlayerInteractEvent event) {
        if (!event.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        Block clickedBlock = event.getBlock();
        Point point = new Point(clickedBlock);
        Region region = point.getCacheRegion(player);

        if (clickedBlock instanceof BlockFenceGate) {
            if (region == null) {
                return;
            }

            if (region.getRole(player.getName()).getId() < Role.Member.getId() && !customMethods.canDoAllCondition.check(player)) {
                player.sendTip("§cУ вас нет доступа к данному региону");
                event.setCancelled(true);
                return;
            }
        }

        if (clickedBlock instanceof BlockTrapdoor) {
            if (region == null) {
                return;
            }

            if (region.getRole(player.getName()).getId() < Role.Member.getId() && !customMethods.canDoAllCondition.check(player)) {
                player.sendTip("§cУ вас нет доступа к данному региону");
                event.setCancelled(true);
                return;
            }
        }

        if (clickedBlock instanceof BlockLoom) {
            if (region == null) {
                return;
            }

            if (region.getRole(player.getName()).getId() < Role.Member.getId() && !customMethods.canDoAllCondition.check(player)) {
                player.sendTip("§cУ вас нет доступа к данному региону");
                event.setCancelled(true);
                return;
            }
        }
        if (clickedBlock instanceof BlockLoom) {
            if (region == null) {
                return;
            }

            if (region.getRole(player.getName()).getId() < Role.Member.getId() && !customMethods.canDoAllCondition.check(player)) {
                player.sendTip("§cУ вас нет доступа к данному региону");
                event.setCancelled(true);
                return;
            }
        }

        event.setCancelled(checkTap(event, region, chests, "chests") || checkTap(event, region, furnaces, "furnace") || checkTap(event, region, redstone, "redstone"));
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onFlintAndSteelUse(PlayerInteractEvent event) {
        if (!event.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        if (player.getInventory().getItemInHand().getId() == Item.FLINT_AND_STEEL) {
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                player.sendTip("§cУ вас нет доступа к данному региону");
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onChestInventoryOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityChest) {
            BlockEntityChest chestBlockEntity = (BlockEntityChest) blockEntity;
            ChestInventory chestInventory = new ChestInventory(chestBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onFurnaceInventoryOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityFurnace) {
            BlockEntityFurnace furnaceBlockEntity = (BlockEntityFurnace) blockEntity;
            FurnaceTypeInventory furnaceInventory = new FurnaceTypeInventory(furnaceBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("furnace"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBarrelOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityBarrel) {
            BlockEntityBarrel barrelBlockEntity = (BlockEntityBarrel) blockEntity;
            BarrelInventory barrelInventory = new BarrelInventory(barrelBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEnchantTableOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityEnchantTable) {
            BlockEntityEnchantTable enchantTableBlockEntity = (BlockEntityEnchantTable) blockEntity;
            EnchantInventory enchantInventory = new EnchantInventory(enchantTableBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onShulkerOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityShulkerBox) {
            BlockEntityShulkerBox shulkerBoxBlockEntity = (BlockEntityShulkerBox) blockEntity;
            ShulkerBoxInventory shulkerBoxInventory = new ShulkerBoxInventory(shulkerBoxBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onCraftingTableOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockCraftingTable) {
            BlockCraftingTable  blockCraftingTable = (BlockCraftingTable) block;
            CraftingTableInventory craftingTableInventory = new CraftingTableInventory(blockCraftingTable);
            Point point = new Point(block);
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onCartographyTableOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockCartographyTable) {
            BlockCartographyTable blockCartographyTable = (BlockCartographyTable) block;
            CartographyTableInventory cartographyTableInventory = new CartographyTableInventory(blockCartographyTable);
            Point point = new Point(block);
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onSmithingTableOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockSmithingTable) {
            BlockSmithingTable blockSmithingTable = (BlockSmithingTable) block;
            SmithingInventory smithingInventory = new SmithingInventory(blockSmithingTable);
            Point point = new Point(block);
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onAnvilTableOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockAnvil) {
            BlockAnvil blockAnvil = (BlockAnvil) block;
            AnvilInventory anvilInventory = new AnvilInventory(blockAnvil);
            Point point = new Point(block);
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBrewingStandOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityBrewingStand) {
            BlockEntityBrewingStand brewingStandBlockEntity = (BlockEntityBrewingStand) blockEntity;
            BrewingInventory brewingInventory = new BrewingInventory(brewingStandBlockEntity);
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onGrindStoneOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockGrindstone) {
            BlockGrindstone blockGrindstone = (BlockGrindstone) block;
            GrindstoneInventory grindstoneInventory = new GrindstoneInventory(blockGrindstone);
            Point point = new Point(block);
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPaintingInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getEntity();

        if (entity instanceof EntityPainting) {
            Point point = new Point(entity.getLocation());
            Region region = point.getCacheRegion(player);

            if (region == null) return;

            if (region.getRole(player.getName()).equals(Role.Nobody) && !customMethods.canDoAllCondition.check(player)) {
                event.setCancelled(true);
                player.sendTip("§cУ вас нет доступа к данному региону");
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onItemFrameOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockEntity blockEntity = block.getLevel().getBlockEntity(block);

        if (blockEntity instanceof BlockEntityItemFrame) {
            Point point = new Point(event.getBlock());
            Region region = point.getCacheRegion(player);

            if (region != null && region.getRole(player.getName()) == Role.Nobody && !customMethods.canDoAllCondition.check(player)) {
                if (!region.getFlag(main.getFlags().get("chests"))) {
                    player.sendTip("§cУ вас нет доступа к данному региону");
                    event.setCancelled(true);

                }
            }
        }
    }
}

