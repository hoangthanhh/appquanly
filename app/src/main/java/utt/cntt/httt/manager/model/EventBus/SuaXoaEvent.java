package utt.cntt.httt.manager.model.EventBus;

import utt.cntt.httt.manager.model.SanPhamMoi;
import utt.cntt.httt.manager.model.User;

public class SuaXoaEvent {
    SanPhamMoi sanPhamMoi;
    User user;

    public SuaXoaEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SuaXoaEvent(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public SanPhamMoi getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}
