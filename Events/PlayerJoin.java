public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player ply = e.getPlayer();
        UUID pID = ply.getUniqueId();

        PermissionAttachment attach = ply.addAttachment(OutcastRanks.getInstance());
        Permissions.getAllPermissions().put(pID.toString(), attach);

        if (RankData.getRankWaitingList().containsKey(ply.getName())) {
            if (!new RankData(RankData.getRankWaitingList().get(ply.getName()), pID).addUserToRank()) {
                new Chat(ply, "&cUnable to add " + ply.getName() + " to the rank '" + RankData.getRankWaitingList().get(pID.toString()) + "'.").message(true);
            }
            else {
                if (!new RankData(RankData.getRankWaitingList().get(pID.toString()), ply.getName()).removeUserFromWaitingList()) {
                    new Chat(ply, "&cUnable to remove " + ply.getName() + " from the rank waiting list.").message(true);
                }
            }
        }

        new RankData(pID).addUserToDefaultRank();
        new RankPermissions(ply).addRankPermissionsToUser();
    }
}
